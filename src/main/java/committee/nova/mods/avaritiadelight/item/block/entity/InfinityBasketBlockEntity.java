package committee.nova.mods.avaritiadelight.item.block.entity;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.registry.ADBlockEntities;
import committee.nova.mods.avaritiadelight.screen.handler.InfinityContainerScreenHandler;
import committee.nova.mods.avaritiadelight.util.ImplementedInventory;
import committee.nova.mods.avaritiadelight.util.InventoryHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.BasketBlock;
import vectorwing.farmersdelight.common.block.entity.Basket;
import vectorwing.farmersdelight.common.block.entity.BasketBlockEntity;

import java.util.List;
import java.util.stream.Collectors;

public class InfinityBasketBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory, Nameable, Basket {
    private final NonNullList<ItemStack> stacks = NonNullList.withSize(243, ItemStack.EMPTY);
    @Nullable
    private Component customName = null;

    public InfinityBasketBlockEntity(BlockPos pos, BlockState state) {
        super(ADBlockEntities.INFINITY_BASKET.get(), pos, state);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        this.stacks.clear();
        InventoryHelper.readNbt(nbt.getCompound("Items"), this.stacks);
        if (nbt.contains("CustomName")) this.customName = Component.Serializer.fromJson(nbt.getString("CustomName"));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("Items", InventoryHelper.writeNbt(new CompoundTag(), this.stacks));
        if (this.customName != null) nbt.putString("CustomName", Component.Serializer.toJson(this.customName));
    }

    @Override
    public @NotNull Component getName() {
        return this.getDisplayName();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return this.customName != null ? this.customName : Component.translatable("block.%s.infinity_cabinet".formatted(AvaritiaDelight.MOD_ID));
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new InfinityContainerScreenHandler(syncId, this, playerInventory);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    public void setCustomName(@Nullable Component customName) {
        this.customName = customName;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public int getMaxStackSize() {
        return Integer.MAX_VALUE;
    }

    @Override
    public double getLevelX() {
        return (double) this.worldPosition.getX() + (double) 0.5F;
    }

    @Override
    public double getLevelY() {
        return (double) this.worldPosition.getY() + (double) 0.5F;
    }

    @Override
    public double getLevelZ() {
        return (double) this.worldPosition.getZ() + (double) 0.5F;
    }

    public static void pushItemsTick(Level level, BlockPos pos, BlockState state, InfinityBasketBlockEntity blockEntity) {
        int facing = state.getValue(BasketBlock.FACING).get3DDataValue();
        if (pullItems(level, blockEntity, facing))
            blockEntity.setChanged();
    }

    public static boolean pullItems(Level level, Basket basket, int facingIndex) {
        for (ItemEntity itementity : getCaptureItems(level, basket, facingIndex)) {
            if (captureItem(basket, itementity)) {
                return true;
            }
        }
        return false;
    }

    public static boolean captureItem(Container inventory, ItemEntity itemEntity) {
        boolean flag = false;
        ItemStack entityItemStack = itemEntity.getItem().copy();
        ItemStack remainderStack = putStackInInventoryAllSlots(inventory, entityItemStack);
        if (remainderStack.isEmpty()) {
            flag = true;
            itemEntity.discard();
        } else {
            itemEntity.setItem(remainderStack);
        }

        return flag;
    }

    public static List<ItemEntity> getCaptureItems(Level level, Basket basket, int facingIndex) {
        return basket.getFacingCollectionArea(facingIndex).toAabbs().stream().flatMap((aabb) -> level.getEntitiesOfClass(ItemEntity.class, aabb.move(basket.getLevelX() - (double) 0.5F, basket.getLevelY() - (double) 0.5F, basket.getLevelZ() - (double) 0.5F), EntitySelector.ENTITY_STILL_ALIVE).stream()).collect(Collectors.toList());
    }

    public static ItemStack putStackInInventoryAllSlots(Container destination, ItemStack stack) {
        int i = destination.getContainerSize();

        for (int j = 0; j < i && !stack.isEmpty(); ++j) {
            stack = insertStack(destination, stack, j);
        }

        return stack;
    }

    private static ItemStack insertStack(Container destination, ItemStack stack, int index) {
        ItemStack itemstack = destination.getItem(index);
        if (canInsertItemInSlot(destination, stack, index,  null)) {
            boolean flag = false;
            boolean isDestinationEmpty = destination.isEmpty();
            if (itemstack.isEmpty()) {
                destination.setItem(index, stack);
                stack = ItemStack.EMPTY;
                flag = true;
            } else if (canCombine(itemstack, stack)) {
                int i = Integer.MAX_VALUE - itemstack.getCount();
                int j = Math.min(stack.getCount(), i);
                stack.shrink(j);
                itemstack.grow(j);
                flag = j > 0;
            }

            if (flag) {
                if (isDestinationEmpty && destination instanceof BasketBlockEntity firstBasket) {
                    if (!firstBasket.mayTransfer()) {
                        int k = 0;
                        firstBasket.setTransferCooldown(8 - k);
                    }
                }

                destination.setChanged();
            }
        }

        return stack;
    }

    private static boolean canInsertItemInSlot(Container inventoryIn, ItemStack stack, int index, @javax.annotation.Nullable Direction side) {
        if (!inventoryIn.canPlaceItem(index, stack)) {
            return false;
        } else {
            return !(inventoryIn instanceof WorldlyContainer) || ((WorldlyContainer) inventoryIn).canPlaceItemThroughFace(index, stack, side);
        }
    }

    private static boolean canCombine(ItemStack stack1, ItemStack stack2) {
        return ItemStack.isSameItemSameTags(stack1, stack2);
    }
}
