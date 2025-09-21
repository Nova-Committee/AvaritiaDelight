package committee.nova.mods.avaritiadelight;

import committee.nova.mods.avaritiadelight.registry.ADRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AvaritiaDelightClient {

    @SubscribeEvent
    public static void onInit(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ADRenderers.registerBlockEntityRenderers();
            ADRenderers.registerRenderLayers();
            ADRenderers.registerScreenFactories();
        });
    }
}
