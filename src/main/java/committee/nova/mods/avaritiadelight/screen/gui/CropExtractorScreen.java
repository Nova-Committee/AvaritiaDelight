package committee.nova.mods.avaritiadelight.screen.gui;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.screen.handler.CropExtractorScreenHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class CropExtractorScreen extends AbstractContainerScreen<CropExtractorScreenHandler> {
    private static final ResourceLocation TEXTURE = ResourceLocation.tryBuild(AvaritiaDelight.MOD_ID, "textures/gui/crop_extractor.png");

    public CropExtractorScreen(CropExtractorScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.imageWidth = 175;
        this.imageHeight = 165;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        context.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        int l = (int) (this.menu.getPercentage() * 26);
        context.blit(TEXTURE, this.leftPos + 72, this.topPos + 37, 176, 46, l, 14, 256, 256);
    }

    @Override
    public void render(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.renderTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        context.drawString(this.font, this.title, 7, 6, 4210752, false);
        context.drawString(this.font, this.playerInventoryTitle, 37, 184, 4210752, false);
    }
}
