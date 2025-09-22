package committee.nova.mods.avaritiadelight.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public final class ADFoods {
    private static final int APPLE_DURATION = Integer.MAX_VALUE;
    public static final FoodProperties BLAZE_TOMATO = new FoodProperties.Builder().nutrition(8).saturationMod(0.6F).build();
    public static final FoodProperties CRYSTAL_CABBAGE = new FoodProperties.Builder().nutrition(8).saturationMod(0.6F).build();
    public static final FoodProperties BLAZE_TOMATO_SAUCE = new FoodProperties.Builder().nutrition(16).saturationMod(1.2F).build();
    public static final FoodProperties CRYSTAL_CABBAGE_LEAF = new FoodProperties.Builder().nutrition(4).saturationMod(0.3F).build();
    public static final FoodProperties INFINITY_APPLE = new FoodProperties.Builder()
            .nutrition(4).saturationMod(0.3F)
            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, APPLE_DURATION), 1)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, APPLE_DURATION, 4), 1)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, APPLE_DURATION, 4), 1)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, APPLE_DURATION, 4), 1)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, APPLE_DURATION, 1), 1)
            .build();
}
