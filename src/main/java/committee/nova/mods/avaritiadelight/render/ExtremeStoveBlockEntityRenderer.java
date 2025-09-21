package committee.nova.mods.avaritiadelight.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import committee.nova.mods.avaritiadelight.item.block.entity.ExtremeStoveBlockEntity;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.block.StoveBlock;

@OnlyIn(Dist.CLIENT)
public class ExtremeStoveBlockEntityRenderer implements BlockEntityRenderer<ExtremeStoveBlockEntity> {
    private final ItemRenderer itemRenderer;

    public ExtremeStoveBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
    }

    @Override
    public void render(ExtremeStoveBlockEntity stoveEntity, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        Direction direction = stoveEntity.getBlockState().getValue(StoveBlock.FACING).getOpposite();
        NonNullList<ItemStack> stacks = stoveEntity.getInventory();
        int posLong = (int) stoveEntity.getBlockPos().asLong();

        for (int i = 0; i < stacks.size(); ++i) {
            ItemStack stoveStack = stacks.get(i);
            if (!stoveStack.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5, 1.02, 0.5);
                float f = -direction.toYRot();
                poseStack.mulPose(Axis.YP.rotationDegrees(f));
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                Vec2 itemOffset = stoveEntity.getStoveItemOffset(i);
                poseStack.translate(itemOffset.x, itemOffset.y, 0.0);
                poseStack.scale(0.375F, 0.375F, 0.375F);
                if (stoveEntity.getLevel() != null)
                    this.itemRenderer.renderStatic(stoveStack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(stoveEntity.getLevel(), stoveEntity.getBlockPos().above()), combinedOverlayIn, poseStack, buffer, stoveEntity.getLevel(), posLong + i);
                poseStack.popPose();
            }
        }
    }
}
