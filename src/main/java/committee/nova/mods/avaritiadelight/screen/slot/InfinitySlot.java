package committee.nova.mods.avaritiadelight.screen.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InfinitySlot extends Slot {
    public InfinitySlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack stack) {
        return this.getMaxStackSize();
    }

    @Override
    public int getMaxStackSize() {
        return Integer.MAX_VALUE;
    }
}
