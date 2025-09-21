package committee.nova.mods.avaritiadelight.screen.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class DisplayOnlySlot extends TakeOnlySlot {
    public DisplayOnlySlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean mayPickup(@NotNull Player playerEntity) {
        return false;
    }
}
