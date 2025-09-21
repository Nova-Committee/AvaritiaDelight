package committee.nova.mods.avaritiadelight;

import com.mojang.logging.LogUtils;
import committee.nova.mods.avaritiadelight.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(AvaritiaDelight.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class AvaritiaDelight {
    public static final String MOD_ID = "avaritia_delight";
    public static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("removal")
    public AvaritiaDelight() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ADBlocks.REGISTRY.register(bus);
        ADBlockEntities.REGISTRY.register(bus);
        ADItems.REGISTRY.register(bus);
        ADItemGroups.REGISTRY.register(bus);
        ADRecipes.TYPE_REGISTRY.register(bus);
        ADRecipes.SERIALIZER_REGISTRY.register(bus);
        ADScreenHandlers.REGISTRY.register(bus);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onBlockBreak(BlockEvent.BreakEvent event) {
            Level world = event.getPlayer().level();
            BlockPos pos = event.getPos();
            BlockState state = event.getState();
            if (world.getBlockState(pos.below()).is(ADBlocks.SOUL_RICH_SOIL_FARMLAND.get()) && state.getBlock() instanceof CropBlock)
                Block.dropResources(state, world, pos);
        }
    }
}
