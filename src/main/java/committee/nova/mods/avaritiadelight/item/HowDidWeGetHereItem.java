package committee.nova.mods.avaritiadelight.item;

import committee.nova.mods.avaritiadelight.util.EffectUtil;
import java.util.List;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.jetbrains.annotations.NotNull;

public class HowDidWeGetHereItem extends Item {
    public HowDidWeGetHereItem() {
        super(EffectUtil.applyEffects(new Properties().stacksTo(1), List.of(
                MobEffects.ABSORPTION,
                MobEffects.BAD_OMEN,
                MobEffects.BLINDNESS,
                MobEffects.CONDUIT_POWER,
                MobEffects.DARKNESS,
                MobEffects.DOLPHINS_GRACE,
                MobEffects.FIRE_RESISTANCE,
                MobEffects.GLOWING,
                MobEffects.DIG_SPEED,
                MobEffects.HERO_OF_THE_VILLAGE,
                MobEffects.HUNGER,
                MobEffects.INVISIBILITY,
                MobEffects.JUMP,
                MobEffects.LEVITATION,
                MobEffects.DIG_SLOWDOWN,
                MobEffects.CONFUSION,
                MobEffects.NIGHT_VISION,
                MobEffects.POISON,
                MobEffects.REGENERATION,
                MobEffects.DAMAGE_RESISTANCE,
                MobEffects.SLOW_FALLING,
                MobEffects.MOVEMENT_SLOWDOWN,
                MobEffects.MOVEMENT_SPEED,
                MobEffects.DAMAGE_BOOST,
                MobEffects.WATER_BREATHING,
                MobEffects.WEAKNESS,
                MobEffects.WITHER
        ), Integer.MAX_VALUE));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }
}
