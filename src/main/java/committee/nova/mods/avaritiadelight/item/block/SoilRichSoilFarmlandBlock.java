package committee.nova.mods.avaritiadelight.item.block;

import committee.nova.mods.avaritiadelight.registry.ADBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.RichSoilFarmlandBlock;

public class SoilRichSoilFarmlandBlock extends RichSoilFarmlandBlock {
    public SoilRichSoilFarmlandBlock(Properties properties) {
        super(properties);
    }

    public static void turnToSoulRichSoil(BlockState state, Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, Block.pushEntitiesUp(state, ADBlocks.SOUL_RICH_SOIL.get().defaultBlockState(), level, pos));
    }

    @Override
    public boolean isFertile(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return state.is(ADBlocks.SOUL_RICH_SOIL_FARMLAND.get()) && state.getValue(MOISTURE) > 0;
    }

    @Override
    public void tick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource rand) {
        if (!state.canSurvive(level, pos))
            turnToSoulRichSoil(state, level, pos);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? ADBlocks.SOUL_RICH_SOIL.get().defaultBlockState() : super.getStateForPlacement(context);
    }
}
