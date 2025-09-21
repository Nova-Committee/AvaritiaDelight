package committee.nova.mods.avaritiadelight.screen.handler;

import committee.nova.mods.avaritiadelight.item.block.entity.ExtremeCookingPotBlockEntity;
import committee.nova.mods.avaritiadelight.registry.ADScreenHandlers;
import committee.nova.mods.avaritiadelight.screen.slot.DisplayOnlySlot;
import committee.nova.mods.avaritiadelight.screen.slot.TakeOnlySlot;
import java.util.Objects;
import net.minecraft.network.FriendlyByteBuf;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class ExtremeCookingPotScreenHandler extends AbstractContainerMenu {
    private final Container inventory;
    private final Inventory playerInventory;
    private final ExtremeCookingPotBlockEntity blockEntity;
    private final ContainerData delegate;

    public ExtremeCookingPotScreenHandler(int syncId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(syncId, new SimpleContainer(84), playerInventory, getBlockEntity(playerInventory, buf), new SimpleContainerData(2));
    }

    public ExtremeCookingPotScreenHandler(int syncId, Container inventory, Inventory playerInventory, ExtremeCookingPotBlockEntity blockEntity, ContainerData delegate) {
        super(ADScreenHandlers.EXTREME_COOKING_POT.get(), syncId);
        checkContainerSize(inventory, 84);
        checkContainerDataCount(delegate, 2);
        this.inventory = inventory;
        this.inventory.startOpen(playerInventory.player);
        this.playerInventory = playerInventory;
        this.blockEntity = blockEntity;
        this.delegate = delegate;
        this.addDataSlots(this.delegate);

        for (int i = 0; i < 9; ++i)
            for (int l = 0; l < 9; ++l)
                this.addSlot(new Slot(inventory, l + i * 9, 8 + l * 18, 18 + i * 18));
        this.addSlot(new DisplayOnlySlot(inventory, ExtremeCookingPotBlockEntity.RESULT_SLOT, 206, 90));//Output
        this.addSlot(new Slot(inventory, ExtremeCookingPotBlockEntity.CONTAINER_SLOT, 174, 125));//Container
        this.addSlot(new TakeOnlySlot(inventory, ExtremeCookingPotBlockEntity.RESULT_WITH_CONTAINER_SLOT, 206, 125));//Final
        for (int i = 0; i < 3; ++i)
            for (int l = 0; l < 9; ++l)
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 39 + l * 18, 196 + i * 18));
        for (int i = 0; i < 9; ++i)
            this.addSlot(new Slot(playerInventory, i, 39 + i * 18, 254));
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
                if (!this.moveItemStackTo(originalStack, 0, 81, false))
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

    @Override
    public void clicked(int slotIndex, int button, @NotNull ClickType actionType, @NotNull Player player) {
        super.clicked(slotIndex, button, actionType, player);
        if (slotIndex >= 0)
            this.slots.get(slotIndex).setChanged();
    }

    public int getCookProgressionScaled() {
        double i = this.delegate.get(0);
        double j = this.delegate.get(1);
        return j != 0 && i != 0 ? (int) (i * 24 / j) : 0;
    }

    public boolean isHeated() {
        return this.blockEntity.isHeated();
    }

    private static ExtremeCookingPotBlockEntity getBlockEntity(Inventory playerInventory, FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        BlockEntity tileAtPos = playerInventory.player.level().getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof ExtremeCookingPotBlockEntity blockEntity) return blockEntity;
        else throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);

    }
}
