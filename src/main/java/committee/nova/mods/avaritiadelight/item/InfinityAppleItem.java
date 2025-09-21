package committee.nova.mods.avaritiadelight.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class InfinityAppleItem extends Item {
    private static final int DURATION = Integer.MAX_VALUE;

    public InfinityAppleItem() {
        super(new Properties().food(new FoodProperties.Builder()
                .nutrition(4).saturationMod(0.3F)
                .effect(new MobEffectInstance(MobEffects.SATURATION, DURATION), 1)
                .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, DURATION, 4), 1)
                .effect(new MobEffectInstance(MobEffects.REGENERATION, DURATION, 4), 1)
                .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, DURATION, 4), 1)
                .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, DURATION, 1), 1)
                .build()));
    }
}
