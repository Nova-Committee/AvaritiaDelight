package committee.nova.mods.avaritiadelight.util;

import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class BlockEntityUtil {
    public static int calcRedstoneFromInventory(@Nullable Container inventory) {
        if (inventory == null) return 0;
        else {
            int i = 0;
            float f = 0.0F;

            for (int j = 0; j < inventory.getContainerSize(); ++j) {
                ItemStack itemstack = inventory.getItem(j);
                if (!itemstack.isEmpty()) {
                    f += (float) itemstack.getCount() / (float) Math.min(inventory.getItem(j).getMaxStackSize(), itemstack.getMaxStackSize());
                    ++i;
                }
            }

            f /= (float) inventory.getContainerSize();
            return Mth.floor(f * 14.0F) + (i > 0 ? 1 : 0);
        }
    }
}
