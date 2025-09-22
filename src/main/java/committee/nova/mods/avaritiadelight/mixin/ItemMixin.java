package committee.nova.mods.avaritiadelight.mixin;

import committee.nova.mods.avaritia.init.registry.ModItems;
import committee.nova.mods.avaritiadelight.registry.ADItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "finishUsingItem", at = @At("RETURN"))
    private void onFinishUsingUltimateStew(ItemStack stack, Level level, LivingEntity living, CallbackInfoReturnable<ItemStack> cir) {
        if (stack.is(ModItems.ultimate_stew.get()) && living instanceof Player player && !player.getAbilities().instabuild)
            player.getInventory().placeItemBackInInventory(new ItemStack(ADItems.NEUTRONIUM_POT.get()));
    }
}
