package committee.nova.mods.avaritiadelight.item;

import committee.nova.mods.avaritiadelight.util.EffectUtil;
import java.util.List;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.jetbrains.annotations.NotNull;

public class FuriousCocktailItem extends Item {
    public FuriousCocktailItem() {
        super(EffectUtil.applyEffects(new Properties().stacksTo(1), List.of(
                MobEffects.FIRE_RESISTANCE,
                MobEffects.INVISIBILITY,
                MobEffects.JUMP,
                MobEffects.NIGHT_VISION,
                MobEffects.POISON,
                MobEffects.REGENERATION,
                MobEffects.DAMAGE_RESISTANCE,
                MobEffects.SLOW_FALLING,
                MobEffects.MOVEMENT_SLOWDOWN,
                MobEffects.MOVEMENT_SPEED,
                MobEffects.DAMAGE_BOOST,
                MobEffects.WATER_BREATHING,
                MobEffects.WEAKNESS
        ), Integer.MAX_VALUE));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }
}
