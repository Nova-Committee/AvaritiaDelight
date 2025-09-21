package committee.nova.mods.avaritiadelight.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ImplementedInventory extends Container {
    static ImplementedInventory of(NonNullList<ItemStack> items) {
        return () -> items;
    }

    static ImplementedInventory ofSize(int size) {
        return of(NonNullList.withSize(size, ItemStack.EMPTY));
    }

    NonNullList<ItemStack> getItems();

    @Override
    default int getContainerSize() {
        return this.getItems().size();
    }

    @Override
    default boolean isEmpty() {
        for (int i = 0; i < this.getContainerSize(); i++) {
            ItemStack stack = this.getItem(i);
            if (!stack.isEmpty())
                return false;
        }
        return true;
    }

    @Override
    default @NotNull ItemStack getItem(int slot) {
        return this.getItems().get(slot);
    }

    @Override
    default @NotNull ItemStack removeItem(int slot, int count) {
        ItemStack result = ContainerHelper.removeItem(this.getItems(), slot, count);
        if (!result.isEmpty()) this.setChanged();
        return result;
    }

    @Override
    default @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.getItems(), slot);
    }

    @Override
    default void setItem(int slot, @NotNull ItemStack stack) {
        this.getItems().set(slot, stack);
    }

    @Override
    default void clearContent() {
        this.getItems().clear();
    }

    @Override
    default void setChanged() {
        // Override if you want behavior.
    }

    @Override
    default boolean stillValid(@NotNull Player player) {
        return true;
    }
}
