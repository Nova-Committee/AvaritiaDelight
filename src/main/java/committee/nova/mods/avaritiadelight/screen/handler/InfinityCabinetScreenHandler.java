package committee.nova.mods.avaritiadelight.screen.handler;

import committee.nova.mods.avaritiadelight.registry.ADScreenHandlers;
import committee.nova.mods.avaritiadelight.screen.slot.InfinitySlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InfinityCabinetScreenHandler extends AbstractContainerMenu {
    private final Container inventory;
    private final Inventory playerInventory;

    public InfinityCabinetScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, new SimpleContainer(243) {
            @Override
            public int getMaxStackSize() {
                return Integer.MAX_VALUE;
            }
        }, playerInventory);
    }

    public InfinityCabinetScreenHandler(int syncId, Container inventory, Inventory playerInventory) {
        super(ADScreenHandlers.INFINITY_CABINET.get(), syncId);
        checkContainerSize(inventory, 243);
        this.inventory = inventory;
        this.inventory.startOpen(playerInventory.player);
        this.playerInventory = playerInventory;

        for (int i = 0; i < 9; ++i)
            for (int l = 0; l < 27; ++l)
                this.addSlot(new InfinitySlot(inventory, l + i * 27, 8 + l * 18, 18 + i * 18));
        for (int i = 0; i < 3; ++i)
            for (int l = 0; l < 9; ++l)
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 170 + l * 18, 194 + i * 18));
        for (int i = 0; i < 9; ++i)
            this.addSlot(new Slot(playerInventory, i, 170 + i * 18, 252));
        this.inventory.setChanged();
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (this.inventory.getContainerSize() <= invSlot && invSlot < this.inventory.getContainerSize() + this.playerInventory.getContainerSize()) {
                if (!this.moveItemStackTo(originalStack, 0, this.inventory.getContainerSize(), false))
                    return ItemStack.EMPTY;
            } else if (!this.moveItemStackTo(originalStack, this.inventory.getContainerSize(), this.inventory.getContainerSize() + 36, false))
                return ItemStack.EMPTY;
            if (originalStack.isEmpty())
                slot.setByPlayer(ItemStack.EMPTY);
            else
                slot.setChanged();
        }
        return newStack;
    }

    public Container getInventory() {
        return this.inventory;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.inventory.stillValid(player);
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.inventory.stopOpen(player);
    }

    @Override
    protected boolean moveItemStackTo(@NotNull ItemStack stack, int startIndex, int endIndex, boolean fromLast) {
        boolean bl = false;
        int i = startIndex;
        if (fromLast)
            i = endIndex - 1;
        Slot slot;
        ItemStack itemStack;
        if (stack.isStackable()) {
            while (!stack.isEmpty()) {
                if (fromLast) {
                    if (i < startIndex) break;
                } else if (i >= endIndex) break;
                slot = this.slots.get(i);
                itemStack = slot.getItem();
                if (!itemStack.isEmpty() && ItemStack.isSameItemSameTags(stack, itemStack)) {
                    int maxCount = i >= this.inventory.getContainerSize() ? 64 : Integer.MAX_VALUE;
                    long j = (long) itemStack.getCount() + stack.getCount();//Use long to prevent overflow
                    if (j <= maxCount) {
                        stack.setCount(0);
                        itemStack.setCount((int) j);
                        slot.setChanged();
                        bl = true;
                    } else if (itemStack.getCount() < maxCount) {
                        stack.shrink(maxCount - itemStack.getCount());
                        itemStack.setCount(maxCount);
                        slot.setChanged();
                        bl = true;
                    }
                }
                if (fromLast) --i;
                else ++i;
            }
        }

        if (!stack.isEmpty()) {
            if (fromLast) i = endIndex - 1;
            else i = startIndex;
            while (true) {
                if (fromLast) {
                    if (i < startIndex) break;
                } else if (i >= endIndex) break;
                slot = this.slots.get(i);
                itemStack = slot.getItem();
                if (itemStack.isEmpty() && slot.mayPlace(stack)) {
                    if (stack.getCount() > slot.getMaxStackSize())
                        slot.setByPlayer(stack.split(slot.getMaxStackSize()));
                    else
                        slot.setByPlayer(stack.split(stack.getCount()));

                    slot.setChanged();
                    bl = true;
                    break;
                }
                if (fromLast) --i;
                else ++i;
            }
        }
        return bl;
    }
}
