package committee.nova.mods.avaritiadelight.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class InfinityMilkItem extends MilkBucketItem {
    public InfinityMilkItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity user) {
        if (user instanceof ServerPlayer serverPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.awardStat(Stats.ITEM_USED.get(this));
        }
        if (!world.isClientSide)
            BuiltInRegistries.MOB_EFFECT.stream().filter(x -> !x.isBeneficial()).forEach(user::removeEffect);
        return stack;
    }
}
