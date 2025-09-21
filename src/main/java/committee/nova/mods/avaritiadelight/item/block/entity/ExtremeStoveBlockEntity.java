package committee.nova.mods.avaritiadelight.item.block.entity;

import committee.nova.mods.avaritiadelight.item.block.ExtremeStoveBlock;
import committee.nova.mods.avaritiadelight.registry.ADBlockEntities;
import committee.nova.mods.avaritiadelight.registry.ADItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.StoveBlock;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.mixin.accessor.RecipeManagerAccessor;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.Optional;

public class ExtremeStoveBlockEntity extends SyncedBlockEntity {
    private static final VoxelShape GRILLING_AREA = Block.box(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
    private static final int INVENTORY_SLOT_COUNT = 6;
    private final NonNullList<ItemStack> stacks = NonNullList.withSize(INVENTORY_SLOT_COUNT, ItemStack.EMPTY);
    private final int[] cookingTimes = new int[6];
    private final int[] cookingTimesTotal = new int[6];
    private final ResourceLocation[] lastRecipeIDs = new ResourceLocation[6];

    public ExtremeStoveBlockEntity(BlockPos pos, BlockState state) {
        super(ADBlockEntities.EXTREME_STOVE.get(), pos, state);
    }

    @Override
    public void load(@NotNull CompoundTag compound) {
        super.load(compound);
        this.stacks.clear();
        if (compound.contains("Inventory"))
            ContainerHelper.loadAllItems(compound.getCompound("Inventory"), this.stacks);
        else
            ContainerHelper.loadAllItems(compound, this.stacks);

        int[] arrayCookingTimesTotal;
        if (compound.contains("CookingTimes", 11)) {
            arrayCookingTimesTotal = compound.getIntArray("CookingTimes");
            System.arraycopy(arrayCookingTimesTotal, 0, this.cookingTimes, 0, Math.min(this.cookingTimesTotal.length, arrayCookingTimesTotal.length));
        }

        if (compound.contains("CookingTotalTimes", 11)) {
            arrayCookingTimesTotal = compound.getIntArray("CookingTotalTimes");
            System.arraycopy(arrayCookingTimesTotal, 0, this.cookingTimesTotal, 0, Math.min(this.cookingTimesTotal.length, arrayCookingTimesTotal.length));
        }

    }

    @Override
    public void saveAdditional(@NotNull CompoundTag compound) {
        this.writeItems(compound);
        compound.putIntArray("CookingTimes", this.cookingTimes);
        compound.putIntArray("CookingTotalTimes", this.cookingTimesTotal);
    }

    private CompoundTag writeItems(CompoundTag compound) {
        super.saveAdditional(compound);
        CompoundTag nbt = new CompoundTag();
        ContainerHelper.saveAllItems(nbt, this.stacks);
        compound.put("Inventory", nbt);
        return compound;
    }

    public static void cookingTick(Level level, BlockPos pos, BlockState state, ExtremeStoveBlockEntity stove) {
        boolean isStoveLit = state.getValue(StoveBlock.LIT);
        if (stove.isStoveBlockedAbove()) {
            for (int i = 0; i < stove.stacks.size(); i++) {
                ItemStack stack = stove.stacks.get(i);
                if (!stack.isEmpty()) {
                    stove.stacks.set(i, ItemStack.EMPTY);
                    Block.popResource(level, pos, stack);
                }
                stove.inventoryChanged();
            }
        } else stove.cookAndOutputItems(isStoveLit);
    }

    public static void animationTick(Level level, BlockPos pos, BlockState state, ExtremeStoveBlockEntity stove) {
        for (int i = 0; i < stove.stacks.size(); ++i) {
            if (!stove.stacks.get(i).isEmpty() && (!stove.stacks.get(i).is(ADItems.COSMIC_BEEF.get()) || state.getValue(ExtremeStoveBlock.LIT)) && level.random.nextFloat() < 0.2F) {
                Vec2 stoveItemVector = stove.getStoveItemOffset(i);
                Direction direction = state.getValue(StoveBlock.FACING);
                int directionIndex = direction.get2DDataValue();
                Vec2 offset = directionIndex % 2 == 0 ? stoveItemVector : new Vec2(stoveItemVector.y, stoveItemVector.x);
                double x = (double) pos.getX() + 0.5 - (double) ((float) direction.getStepX() * offset.x) + (double) ((float) direction.getClockWise().getStepX() * offset.x);
                double y = (double) pos.getY() + 1.0;
                double z = (double) pos.getZ() + 0.5 - (double) ((float) direction.getStepZ() * offset.y) + (double) ((float) direction.getClockWise().getStepZ() * offset.y);

                for (int k = 0; k < 3; ++k)
                    level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 5.0E-4, 0.0);
            }
        }
    }

    private void cookAndOutputItems(boolean lit) {
        if (this.level != null) {
            boolean didInventoryChange = false;

            for (int i = 0; i < this.stacks.size(); ++i) {
                ItemStack stoveStack = this.stacks.get(i);
                if (lit || !stoveStack.is(ADItems.COSMIC_BEEF.get())) {
                    if (!stoveStack.isEmpty()) {
                        this.cookingTimes[i]++;
                        if (stoveStack.is(ADItems.COSMIC_BEEF.get())) {
                            if (this.cookingTimes[i] >= 10 * 20) {
                                ItemUtils.spawnItemEntity(this.level, new ItemStack(ADItems.COSMIC_BEEF_COOKED.get()), (double) this.worldPosition.getX() + 0.5, (double) this.worldPosition.getY() + 1.0, (double) this.worldPosition.getZ() + 0.5, this.level.random.nextGaussian() * 0.009999999776482582, 0.10000000149011612, this.level.random.nextGaussian() * 0.009999999776482582);
                                this.stacks.set(i, ItemStack.EMPTY);
                                didInventoryChange = true;
                            }
                        } else if (this.cookingTimes[i] >= 20) {
                            Container inventoryWrapper = new SimpleContainer(stoveStack);
                            Optional<CampfireCookingRecipe> recipe = this.getMatchingRecipe(inventoryWrapper, i);
                            if (recipe.isPresent()) {
                                ItemStack resultStack = recipe.get().getResultItem(this.level.registryAccess());
                                if (!resultStack.isEmpty())
                                    ItemUtils.spawnItemEntity(this.level, resultStack.copy(), (double) this.worldPosition.getX() + 0.5, (double) this.worldPosition.getY() + 1.0, (double) this.worldPosition.getZ() + 0.5, this.level.random.nextGaussian() * 0.009999999776482582, 0.10000000149011612, this.level.random.nextGaussian() * 0.009999999776482582);
                            }

                            this.stacks.set(i, ItemStack.EMPTY);
                            didInventoryChange = true;
                        }
                    }
                } else if (this.cookingTimes[i] > 0)
                    this.cookingTimes[i] = Mth.clamp(this.cookingTimes[i] - 2, 0, this.cookingTimesTotal[i]);
            }
            if (didInventoryChange) this.inventoryChanged();
        }
    }

    public int getNextEmptySlot() {
        for (int i = 0; i < this.stacks.size(); ++i) {
            ItemStack slotStack = this.stacks.get(i);
            if (slotStack.isEmpty())
                return i;
        }

        return -1;
    }

    public boolean addItem(ItemStack itemStackIn, CampfireCookingRecipe recipe, int slot) {
        if (0 <= slot && slot < this.stacks.size()) {
            ItemStack slotStack = this.stacks.get(slot);
            if (slotStack.isEmpty()) {
                this.cookingTimesTotal[slot] = recipe.getCookingTime();
                this.cookingTimes[slot] = 0;
                this.stacks.set(slot, itemStackIn.split(1));
                this.lastRecipeIDs[slot] = recipe.getId();
                this.inventoryChanged();
                return true;
            }
        }
        return false;
    }

    public Optional<CampfireCookingRecipe> getMatchingRecipe(Container recipeWrapper, int slot) {
        if (this.level == null)
            return Optional.empty();
        else {
            if (this.lastRecipeIDs[slot] != null) {
                Recipe<Container> recipe = ((RecipeManagerAccessor) this.level.getRecipeManager()).getRecipeMap(RecipeType.CAMPFIRE_COOKING).get(this.lastRecipeIDs[slot]);
                if (recipe instanceof CampfireCookingRecipe cookingRecipe && recipe.matches(recipeWrapper, this.level))
                    return Optional.of(cookingRecipe);
            }
            return this.level.getRecipeManager().getRecipeFor(RecipeType.CAMPFIRE_COOKING, recipeWrapper, this.level);
        }
    }

    public NonNullList<ItemStack> getInventory() {
        return this.stacks;
    }

    public boolean isStoveBlockedAbove() {
        if (this.level != null) {
            BlockState above = this.level.getBlockState(this.worldPosition.above());
            return Shapes.joinIsNotEmpty(GRILLING_AREA, above.getShape(this.level, this.worldPosition.above()), BooleanOp.AND);
        } else {
            return false;
        }
    }

    public Vec2 getStoveItemOffset(int index) {
        Vec2[] OFFSETS = new Vec2[]{new Vec2(0.3F, 0.2F), new Vec2(0.0F, 0.2F), new Vec2(-0.3F, 0.2F), new Vec2(0.3F, -0.2F), new Vec2(0.0F, -0.2F), new Vec2(-0.3F, -0.2F)};
        return OFFSETS[index];
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return this.writeItems(new CompoundTag());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
