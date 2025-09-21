package committee.nova.mods.avaritiadelight.screen.gui;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.screen.handler.InfinityCabinetScreenHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class InfinityCabinetScreen extends AbstractContainerScreen<InfinityCabinetScreenHandler> {
    private static final ResourceLocation TEXTURE = ResourceLocation.tryBuild(AvaritiaDelight.MOD_ID, "textures/gui/infinity_cabinet.png");

    public InfinityCabinetScreen(InfinityCabinetScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.imageWidth = 499;
        this.imageHeight = 275;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        context.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 500, 500);
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
        context.drawString(this.font, this.playerInventoryTitle, 169, 182, 4210752, false);
    }
}
