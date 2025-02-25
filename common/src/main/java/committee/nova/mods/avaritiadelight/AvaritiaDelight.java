package committee.nova.mods.avaritiadelight;

import com.mojang.logging.LogUtils;
import committee.nova.mods.avaritiadelight.registry.*;
import committee.nova.mods.avaritiadelight.util.BlockEntityUtil;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntityType;
import org.slf4j.Logger;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

public final class AvaritiaDelight {
    public static final String MOD_ID = "avaritia_delight";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        ADBlocks.REGISTRY.register();
        ADBlockEntities.REGISTRY.register();
        ADItems.REGISTRY.register();
        ADItemGroups.REGISTRY.register();
        ADRecipes.TYPE_REGISTRY.register();
        ADRecipes.SERIALIZER_REGISTRY.register();
    }

    public static void process() {
        BlockEntityUtil.appendBlockToType(ModBlockEntityTypes.STOVE.get(), ADBlocks.EXTREME_STOVE.get());
        BlockEntityUtil.appendBlockToType(BlockEntityType.BARREL, ADBlocks.INFINITY_CABINET.get());
        BlockEvent.BREAK.register((world, pos, state, player, intValue) -> {
            BlockState down = world.getBlockState(pos.down());
            if (down.isOf(ADBlocks.SOUL_RICH_SOIL_FARMLAND.get()) && state.getBlock() instanceof CropBlock)
                Block.dropStacks(state, world, pos);
            return EventResult.pass();
        });
    }
}
