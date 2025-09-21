package committee.nova.mods.avaritiadelight.mixin;

import committee.nova.mods.avaritia.Const;
import committee.nova.mods.avaritiadelight.registry.ADItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @SuppressWarnings({"ConstantValue", "deprecation"})
    @Inject(method = "eat", at = @At("RETURN"))
    private void onFinishUsingUntimateStew(Level world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (stack.is(BuiltInRegistries.ITEM.get(ResourceLocation.tryBuild(Const.MOD_ID, "ultimate_stew"))) && (Object) this instanceof Player player && !player.getAbilities().instabuild)
            player.getInventory().placeItemBackInInventory(new ItemStack(ADItems.NEUTRONIUM_POT.get()));
    }
}
