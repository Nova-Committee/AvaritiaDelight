package committee.nova.mods.avaritiadelight.util;

import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class EffectUtil {
    public static Item.Properties applyEffects(Item.Properties settings, List<MobEffect> effects, int duration) {
        FoodProperties.Builder builder = new FoodProperties.Builder();
        for (MobEffect effect : effects)
            builder.effect(new MobEffectInstance(effect, duration), 1);
        return settings.food(builder.build());
    }
}
