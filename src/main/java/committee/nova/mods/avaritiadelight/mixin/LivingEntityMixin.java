package committee.nova.mods.avaritiadelight.mixin;

import committee.nova.mods.avaritiadelight.registry.ADItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @SuppressWarnings("ConstantValue")
    @Inject(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"))
    private void onFinishUsingUltimateStew(Level level, ItemStack food, CallbackInfoReturnable<ItemStack> cir) {
        if (food.is(ForgeRegistries.ITEMS.getValue(ResourceLocation.tryBuild("avaritia", "ultimate_stew"))) && (Object) this instanceof Player player)
            player.getInventory().placeItemBackInInventory(new ItemStack(ADItems.NEUTRONIUM_POT.get()));
    }
}
