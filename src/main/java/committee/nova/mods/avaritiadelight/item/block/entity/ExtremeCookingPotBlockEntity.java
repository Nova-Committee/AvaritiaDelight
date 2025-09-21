package committee.nova.mods.avaritiadelight.item.block.entity;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.recipe.ExtremeCookingPotRecipe;
import committee.nova.mods.avaritiadelight.recipe.ExtremeCookingPotShapedRecipe;
import committee.nova.mods.avaritiadelight.recipe.ExtremeCookingPotShapelessRecipe;
import committee.nova.mods.avaritiadelight.registry.ADBlockEntities;
import committee.nova.mods.avaritiadelight.screen.handler.ExtremeCookingPotScreenHandler;
import committee.nova.mods.avaritiadelight.util.ImplementedInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.Optional;

public class ExtremeCookingPotBlockEntity extends SyncedBlockEntity implements MenuProvider, ImplementedInventory, HeatableBlockEntity, Nameable {
    public static final int RESULT_SLOT = 81, CONTAINER_SLOT = 82, RESULT_WITH_CONTAINER_SLOT = 83;
    private final NonNullList<ItemStack> stacks = NonNullList.withSize(84, ItemStack.EMPTY);
    @Nullable
    private Component customName;
    private int cookTime;
    private int cookTimeTotal;
    @Nullable
    private ItemStack mealContainerStack;
    private final ContainerData cookingProgress = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> ExtremeCookingPotBlockEntity.this.cookTime;
                case 1 -> ExtremeCookingPotBlockEntity.this.cookTimeTotal;
                default -> throw new IllegalStateException("Unexpected value: " + index);
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    ExtremeCookingPotBlockEntity.this.cookTime = value;
                case 1:
                    ExtremeCookingPotBlockEntity.this.cookTimeTotal = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public ExtremeCookingPotBlockEntity(BlockPos pos, BlockState state) {
        super(ADBlockEntities.EXTREME_COOKING_POT.get(), pos, state);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        this.stacks.clear();
        ContainerHelper.loadAllItems(nbt.getCompound("Items"), this.stacks);
        if (nbt.contains("CustomName"))
            this.customName = Component.Serializer.fromJson(nbt.getString("CustomName"));
        this.cookTime = nbt.getInt("cookTime");
        this.cookTimeTotal = nbt.getInt("cookTimeTotal");
        if (nbt.contains("mealContainerStack"))
            this.mealContainerStack = ItemStack.of(nbt.getCompound("mealContainerStack"));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("Items", ContainerHelper.saveAllItems(new CompoundTag(), this.stacks));
        if (this.customName != null)
            nbt.putString("CustomName", Component.Serializer.toJson(this.customName));
        nbt.putInt("cookTime", this.cookTime);
        nbt.putInt("cookTimeTotal", this.cookTimeTotal);
        if (this.mealContainerStack != null)
            nbt.put("mealContainerStack", this.mealContainerStack.save(new CompoundTag()));
    }

    @Override
    public @NotNull Component getName() {
        return this.customName;
    }

    public void setCustomName(@Nullable Component customName) {
        this.customName = customName;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return this.customName != null ? this.customName : Component.translatable("block.%s.extreme_cooking_pot".formatted(AvaritiaDelight.MOD_ID));
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new ExtremeCookingPotScreenHandler(syncId, this, playerInventory, this, this.cookingProgress);
    }

    public void saveExtraData(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.worldPosition);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    public boolean isHeated() {
        return this.level != null && this.isHeated(this.level, this.worldPosition);
    }

    private boolean hasInput() {
        for (int i = 0; i < RESULT_SLOT; ++i)
            if (!this.getItem(i).isEmpty())
                return true;
        return false;
    }

    public static void cookingTick(Level level, BlockPos pos, BlockState state, ExtremeCookingPotBlockEntity cookingPot) {
        boolean isHeated = cookingPot.isHeated(level, pos);
        boolean didInventoryChange = false;
        if (isHeated && cookingPot.hasInput()) {
            Optional<ExtremeCookingPotShapedRecipe> recipe = level.getRecipeManager().getRecipeFor(ExtremeCookingPotShapedRecipe.Type.INSTANCE, cookingPot, level);
            if (recipe.isPresent() && cookingPot.canCook(recipe.get()))
                didInventoryChange = cookingPot.processCooking(recipe.get());
            else {
                Optional<ExtremeCookingPotShapelessRecipe> recipe2 = level.getRecipeManager().getRecipeFor(ExtremeCookingPotShapelessRecipe.Type.INSTANCE, cookingPot, level);
                if (recipe2.isPresent() && cookingPot.canCook(recipe2.get()))
                    didInventoryChange = cookingPot.processCooking(recipe2.get());
                else
                    cookingPot.cookTime = 0;
            }
        } else if (cookingPot.cookTime > 0)
            cookingPot.cookTime = Mth.clamp(cookingPot.cookTime - 2, 0, cookingPot.cookTimeTotal);

        ItemStack mealStack = cookingPot.getMeal();
        if (!mealStack.isEmpty()) {
            if (!cookingPot.doesMealHaveContainer(mealStack)) {
                cookingPot.moveMealToOutput();
                didInventoryChange = true;
            } else if (!cookingPot.getItem(CONTAINER_SLOT).isEmpty()) {
                cookingPot.useStoredContainersOnMeal();
                didInventoryChange = true;
            }
        }

        if (didInventoryChange)
            cookingPot.inventoryChanged();
    }

    private void moveMealToOutput() {
        ItemStack mealStack = this.getItem(RESULT_SLOT);
        ItemStack outputStack = this.getItem(RESULT_WITH_CONTAINER_SLOT);
        int mealCount = Math.min(mealStack.getCount(), mealStack.getMaxStackSize() - outputStack.getCount());
        if (outputStack.isEmpty()) {
            this.setItem(RESULT_WITH_CONTAINER_SLOT, mealStack.split(mealCount));
        } else if (outputStack.getItem() == mealStack.getItem()) {
            mealStack.shrink(mealCount);
            outputStack.grow(mealCount);
        }

    }

    protected boolean canCook(ExtremeCookingPotRecipe recipe) {
        if (this.hasInput()) {
            assert this.level != null;
            ItemStack resultStack = recipe.getResultItem(this.level.registryAccess());
            if (resultStack.isEmpty())
                return false;
            else {
                ItemStack storedMealStack = this.getItem(RESULT_SLOT);
                if (storedMealStack.isEmpty())
                    return true;
                else if (!ItemStack.isSameItem(storedMealStack, resultStack))
                    return false;
                else if (storedMealStack.getCount() + resultStack.getCount() <= this.getItem(RESULT_SLOT).getMaxStackSize())
                    return true;
                else
                    return storedMealStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize();

            }
        } else return false;
    }

    private boolean processCooking(ExtremeCookingPotRecipe recipe) {
        if (this.level == null) return false;
        else {
            ++this.cookTime;
            this.cookTimeTotal = recipe.getCookTime();
            if (this.cookTime < this.cookTimeTotal) return false;
            else {
                this.cookTime = 0;
                this.mealContainerStack = recipe.getOutputContainer();
                ItemStack resultStack = recipe.getResultItem(this.level.registryAccess());
                ItemStack storedMealStack = this.getItem(RESULT_SLOT);
                if (storedMealStack.isEmpty())
                    this.setItem(RESULT_SLOT, resultStack.copy());
                else if (ItemStack.isSameItem(storedMealStack, resultStack))
                    storedMealStack.grow(resultStack.getCount());

                //FIXME:: Unlocker?
//                cookingPot.setLastRecipe(recipe);

                for (int i = 0; i < 81; ++i) {
                    ItemStack slotStack = this.getItem(i);
                    Item remainder = slotStack.getItem().getCraftingRemainingItem();
                    if (remainder != null) this.ejectIngredientRemainder(remainder.getDefaultInstance());
                    else if (CookingPotBlockEntity.INGREDIENT_REMAINDER_OVERRIDES.containsKey(slotStack.getItem()))
                        this.ejectIngredientRemainder(CookingPotBlockEntity.INGREDIENT_REMAINDER_OVERRIDES.get(slotStack.getItem()).getDefaultInstance());
                    if (!slotStack.isEmpty())
                        slotStack.shrink(1);
                }
                return true;
            }
        }
    }

    protected void ejectIngredientRemainder(ItemStack remainderStack) {
        Direction direction = this.getBlockState().getValue(CookingPotBlock.FACING).getCounterClockWise();
        double x = this.worldPosition.getX() + 0.5 + direction.getStepX() * 0.25;
        double y = this.worldPosition.getY() + 0.7;
        double z = this.worldPosition.getZ() + 0.5 + direction.getStepZ() * 0.25;
        assert this.level != null;
        ItemUtils.spawnItemEntity(this.level, remainderStack, x, y, z, direction.getStepX() * 0.08, 0.25, direction.getStepZ() * 0.08);
    }

    private void useStoredContainersOnMeal() {
        ItemStack mealStack = this.getItem(RESULT_SLOT);
        ItemStack containerInputStack = this.getItem(CONTAINER_SLOT);
        ItemStack outputStack = this.getItem(RESULT_WITH_CONTAINER_SLOT);
        if (this.isContainerValid(containerInputStack) && outputStack.getCount() < outputStack.getMaxStackSize()) {
            int smallerStackCount = Math.min(mealStack.getCount(), containerInputStack.getCount());
            int mealCount = Math.min(smallerStackCount, mealStack.getMaxStackSize() - outputStack.getCount());
            if (outputStack.isEmpty()) {
                containerInputStack.shrink(mealCount);
                this.setItem(RESULT_WITH_CONTAINER_SLOT, mealStack.split(mealCount));
            } else if (outputStack.getItem() == mealStack.getItem()) {
                mealStack.shrink(mealCount);
                containerInputStack.shrink(mealCount);
                outputStack.grow(mealCount);
            }
        }

    }

    public ItemStack getMeal() {
        return this.getItem(RESULT_SLOT);
    }

    private boolean doesMealHaveContainer(ItemStack meal) {
        assert this.mealContainerStack != null;
        return !this.mealContainerStack.isEmpty() || meal.getItem().hasCraftingRemainingItem();
    }

    public boolean isContainerValid(ItemStack containerItem) {
        if (containerItem.isEmpty()) return false;
        assert this.mealContainerStack != null;
        return !this.mealContainerStack.isEmpty() ? ItemStack.isSameItem(this.mealContainerStack, containerItem) : ItemStack.isSameItem(this.getMeal(), containerItem);
    }

    public ItemStack useHeldItemOnMeal(ItemStack container) {
        if (this.isContainerValid(container) && !this.getMeal().isEmpty()) {
            container.shrink(1);
            this.inventoryChanged();
            return this.getMeal().split(1);
        } else return ItemStack.EMPTY;
    }

    public static void animationTick(Level level, BlockPos pos, BlockState state, ExtremeCookingPotBlockEntity cookingPot) {
        if (cookingPot.isHeated(level, pos)) {
            RandomSource random = level.random;
            double x;
            double y;
            double z;
            if (random.nextFloat() < 0.2F) {
                x = (double) pos.getX() + 0.5 + (random.nextDouble() * 0.6 - 0.3);
                y = (double) pos.getY() + 0.7;
                z = (double) pos.getZ() + 0.5 + (random.nextDouble() * 0.6 - 0.3);
                level.addParticle(ParticleTypes.BUBBLE_POP, x, y, z, 0.0, 0.0, 0.0);
            }

            if (random.nextFloat() < 0.05F) {
                x = (double) pos.getX() + 0.5 + (random.nextDouble() * 0.4 - 0.2);
                y = (double) pos.getY() + 0.5;
                z = (double) pos.getZ() + 0.5 + (random.nextDouble() * 0.4 - 0.2);
                double motionY = random.nextBoolean() ? 0.015 : 0.005;
                level.addParticle((SimpleParticleType) BuiltInRegistries.PARTICLE_TYPE.get(ResourceLocation.tryBuild(FarmersDelight.MODID, "steam")), x, y, z, 0.0, motionY, 0.0);
            }
        }
    }
}
