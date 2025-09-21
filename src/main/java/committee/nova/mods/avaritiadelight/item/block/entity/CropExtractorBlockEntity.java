package committee.nova.mods.avaritiadelight.item.block.entity;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.recipe.CropExtractorRecipe;
import committee.nova.mods.avaritiadelight.registry.ADBlockEntities;
import committee.nova.mods.avaritiadelight.screen.handler.CropExtractorScreenHandler;
import committee.nova.mods.avaritiadelight.util.ImplementedInventory;
import committee.nova.mods.avaritiadelight.util.InventoryHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CropExtractorBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory {
    private final NonNullList<ItemStack> stacks = NonNullList.withSize(5, ItemStack.EMPTY);
    private int time;
    private int timeTotal;
    private CropExtractorRecipe lastRecipe = null;
    private final ContainerData cookingProgress = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> CropExtractorBlockEntity.this.time;
                case 1 -> CropExtractorBlockEntity.this.timeTotal;
                default -> throw new IllegalStateException("Unexpected value: " + index);
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    CropExtractorBlockEntity.this.time = value;
                case 1:
                    CropExtractorBlockEntity.this.timeTotal = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public CropExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(ADBlockEntities.CROP_EXTRACTOR.get(), pos, state);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.%s.crop_extractor".formatted(AvaritiaDelight.MOD_ID));
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new CropExtractorScreenHandler(syncId, this, playerInventory, this.cookingProgress);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        ContainerHelper.loadAllItems(nbt.getCompound("Items"), this.stacks);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("Items", InventoryHelper.writeNbt(new CompoundTag(), this.stacks));
    }

    public static void tick(Level world, BlockPos pos, BlockState state, CropExtractorBlockEntity blockEntity) {
        CropExtractorRecipe matched = world.getRecipeManager().getRecipeFor(CropExtractorRecipe.Type.INSTANCE, blockEntity, world).orElse(null);
        if (blockEntity.lastRecipe != null && matched != null && Objects.equals(blockEntity.lastRecipe.getId(), matched.getId()) && InventoryHelper.canInsertItems(1, blockEntity, matched.getOutputs())) {
            blockEntity.time++;
            if (blockEntity.time >= blockEntity.timeTotal) {
                blockEntity.time = 0;
                blockEntity.getItem(0).shrink(1);
                InventoryHelper.insertItems(1, blockEntity, matched.getOutputs());
            }
        } else {
            blockEntity.lastRecipe = matched;
            blockEntity.time = 0;
            blockEntity.timeTotal = matched == null ? 0 : matched.getTime();
        }
    }
}
