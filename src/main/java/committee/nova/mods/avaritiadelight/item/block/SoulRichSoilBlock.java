package committee.nova.mods.avaritiadelight.item.block;

import committee.nova.mods.avaritiadelight.registry.ADBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.RichSoilBlock;

public class SoulRichSoilBlock extends RichSoilBlock {
    public SoulRichSoilBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(ItemTags.HOES)) {
            world.setBlockAndUpdate(pos, ADBlocks.SOUL_RICH_SOIL_FARMLAND.get().defaultBlockState());
            stack.hurtAndBreak(1, player, living -> living.broadcastBreakEvent(hand));
            world.playLocalSound(pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1, 0, true);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, world, pos, player, hand, hit);
    }
}
