package committee.nova.mods.avaritiadelight.screen.gui;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.screen.handler.ExtremeCookingPotScreenHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.utility.TextUtils;

public class ExtremeCookingPotScreen extends AbstractContainerScreen<ExtremeCookingPotScreenHandler> {
    private static final ResourceLocation TEXTURE = ResourceLocation.tryBuild(AvaritiaDelight.MOD_ID, "textures/gui/extreme_cooking_pot.png");

    public ExtremeCookingPotScreen(ExtremeCookingPotScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.imageWidth = 234;
        this.imageHeight = 277;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        context.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 512, 512);
        int l = this.menu.getCookProgressionScaled();
        context.blit(TEXTURE, this.leftPos + 172, this.topPos + 90, 234, 15, l + 1, 17, 512, 512);
    }

    @Override
    public void render(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        boolean heated = this.menu.isHeated();
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        if (heated)
            context.blit(TEXTURE, this.leftPos + 177, this.topPos + 66, 234, 0, 17, 15, 512, 512);
        this.renderTooltip(context, mouseX, mouseY);
        if (this.isHovering(174, 63, 21, 21, mouseX, mouseY)) {
            String key = "container.cooking_pot." + (heated ? "heated" : "not_heated");
            context.renderTooltip(this.font, TextUtils.getTranslation(key, this.menu), mouseX, mouseY);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        context.drawString(this.font, this.title, 7, 6, 4210752, false);
        context.drawString(this.font, this.playerInventoryTitle, 37, 184, 4210752, false);
    }
}
