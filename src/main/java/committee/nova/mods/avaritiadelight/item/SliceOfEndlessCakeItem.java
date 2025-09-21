package committee.nova.mods.avaritiadelight.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SliceOfEndlessCakeItem extends Item {
    public SliceOfEndlessCakeItem() {
        super(new Properties());
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof Player player && world.getGameTime() % 10 == 0) {
            FoodData hungerManager = player.getFoodData();
            hungerManager.addExhaustion(1);
            hungerManager.setSaturation(hungerManager.getSaturationLevel() + 1);
        }
    }
}
