package committee.nova.mods.avaritiadelight.screen.handler;

import committee.nova.mods.avaritiadelight.registry.ADScreenHandlers;
import committee.nova.mods.avaritiadelight.screen.slot.TakeOnlySlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CropExtractorScreenHandler extends AbstractContainerMenu {
    private final Container inventory;
    private final Inventory playerInventory;
    private final ContainerData delegate;

    public CropExtractorScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, new SimpleContainer(5), playerInventory, new SimpleContainerData(2));
    }

    public CropExtractorScreenHandler(int syncId, Container inventory, Inventory playerInventory, ContainerData delegate) {
        super(ADScreenHandlers.CROP_EXTRACTOR.get(), syncId);
        checkContainerSize(inventory, 5);
        checkContainerDataCount(delegate, 2);
        this.inventory = inventory;
        this.inventory.startOpen(playerInventory.player);
        this.playerInventory = playerInventory;
        this.delegate = delegate;
        this.addDataSlots(this.delegate);

        this.addSlot(new Slot(inventory, 0, 44, 35));
        for (int i = 0; i < 2; ++i)
            for (int l = 0; l < 2; ++l)
                this.addSlot(new TakeOnlySlot(inventory, l + i * 2 + 1, 107 + l * 18, 26 + i * 18));

        for (int i = 0; i < 3; ++i)
            for (int l = 0; l < 9; ++l)
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
        for (int i = 0; i < 9; ++i)
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
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
                if (!this.moveItemStackTo(originalStack, 0, 1, false))
                    return ItemStack.EMPTY;
            } else if (!this.moveItemStackTo(originalStack, this.inventory.getContainerSize(), this.inventory.getContainerSize() + 36, false))
                return ItemStack.EMPTY;
            if (originalStack.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return newStack;
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

    public double getPercentage() {
        return 1.0 * this.delegate.get(0) / this.delegate.get(1);
    }

    @Override
    public void clicked(int slotIndex, int button, @NotNull ClickType actionType, @NotNull Player player) {
        super.clicked(slotIndex, button, actionType, player);
        if (slotIndex >= 0)
            this.slots.get(slotIndex).setChanged();
    }
}
