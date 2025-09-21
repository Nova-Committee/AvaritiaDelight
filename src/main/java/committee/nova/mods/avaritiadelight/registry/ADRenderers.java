package committee.nova.mods.avaritiadelight.registry;

import committee.nova.mods.avaritiadelight.render.ExtremeStoveBlockEntityRenderer;
import committee.nova.mods.avaritiadelight.screen.gui.CropExtractorScreen;
import committee.nova.mods.avaritiadelight.screen.gui.ExtremeCookingPotScreen;
import committee.nova.mods.avaritiadelight.screen.gui.InfinityCabinetScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ADRenderers {
    public static void registerBlockEntityRenderers() {
        BlockEntityRenderers.register(ADBlockEntities.EXTREME_STOVE.get(), ExtremeStoveBlockEntityRenderer::new);
    }

    @SuppressWarnings("removal")
    public static void registerRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(ADBlocks.BLAZE_TOMATO.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ADBlocks.BUDDING_BLAZE_TOMATO.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ADBlocks.CRYSTAL_CABBAGE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ADBlocks.DIAMOND_LATTICE_POTATO.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ADBlocks.NEUTRONIUM_WHEAT.get(), RenderType.cutout());
    }

    public static void registerScreenFactories() {
        MenuScreens.register(ADScreenHandlers.EXTREME_COOKING_POT.get(), ExtremeCookingPotScreen::new);
        MenuScreens.register(ADScreenHandlers.INFINITY_CABINET.get(), InfinityCabinetScreen::new);
        MenuScreens.register(ADScreenHandlers.CROP_EXTRACTOR.get(), CropExtractorScreen::new);
    }
}
