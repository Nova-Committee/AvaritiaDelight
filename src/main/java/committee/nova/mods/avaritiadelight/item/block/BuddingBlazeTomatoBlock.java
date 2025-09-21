package committee.nova.mods.avaritiadelight.item.block;

import committee.nova.mods.avaritiadelight.registry.ADBlocks;
import committee.nova.mods.avaritiadelight.registry.ADItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.BuddingTomatoBlock;

public class BuddingBlazeTomatoBlock extends BuddingTomatoBlock {
    public BuddingBlazeTomatoBlock() {
        super(Properties.copy(Blocks.WHEAT));
    }

    @Override
    public @NotNull BlockState getPlant(@NotNull BlockGetter world, @NotNull BlockPos pos) {
        return ADBlocks.BUDDING_BLAZE_TOMATO.get().defaultBlockState();
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (state.getValue(AGE) == 3)
            level.setBlock(currentPos, ADBlocks.BLAZE_TOMATO.get().defaultBlockState(), 3);
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public void growPastMaxAge(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        level.setBlockAndUpdate(pos, ADBlocks.BLAZE_TOMATO.get().defaultBlockState());
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        int maxAge = this.getMaxAge();
        int ageGrowth = Math.min(this.getAge(state) + this.getBonemealAgeIncrease(level), 7);
        if (ageGrowth <= maxAge)
            level.setBlockAndUpdate(pos, state.setValue(AGE, ageGrowth));
        else {
            int remainingGrowth = ageGrowth - maxAge - 1;
            level.setBlockAndUpdate(pos, ADBlocks.BLAZE_TOMATO.get().defaultBlockState().setValue(BlazeTomatoBlock.VINE_AGE, remainingGrowth));
        }
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ADItems.BLAZE_TOMATO_SEEDS.get();
    }
}
