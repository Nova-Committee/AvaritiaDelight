package committee.nova.mods.avaritiadelight.mixin;

import committee.nova.mods.avaritiadelight.registry.ADItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.utility.ItemUtils;

@Pseudo
@Mixin(value = CuttingBoardBlockEntity.class, remap = false)
public abstract class CuttingBoardBlockEntityMixin extends SyncedBlockEntity {
    @Unique
    private ItemStack avaritia_delight$tempToolStack = ItemStack.EMPTY;

    public CuttingBoardBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Inject(method = "processStoredItemUsingTool", at = @At(value = "HEAD"))
    private void beforeCutting(ItemStack toolStack, Player player, CallbackInfoReturnable<Boolean> cir) {
        this.avaritia_delight$tempToolStack = toolStack;
    }

    @Inject(method = "processStoredItemUsingTool", at = @At(value = "TAIL"))
    private void afterCutting(ItemStack toolStack, Player player, CallbackInfoReturnable<Boolean> cir) {
        this.avaritia_delight$tempToolStack = ItemStack.EMPTY;
    }

    @Inject(method = "removeItem", at = @At("HEAD"))
    private void dropBeef(CallbackInfoReturnable<ItemStack> cir) {
        assert this.level != null;
        if (!this.remove && (this.avaritia_delight$tempToolStack.is(ADItems.NEUTRONIUM_KNIFE.get()) || this.avaritia_delight$tempToolStack.is(ADItems.INFINITY_KNIFE.get())) && this.level.random.nextInt(10) == 0) {
            Direction direction = this.getBlockState().getValue(CuttingBoardBlock.FACING).getCounterClockWise();
            ItemUtils.spawnItemEntity(this.level, new ItemStack(ADItems.COSMIC_BEEF.get()), this.worldPosition.getX() + 0.5 + direction.getStepX() * 0.2, this.worldPosition.getY() + 0.2, this.worldPosition.getZ() + 0.5 + direction.getStepZ() * 0.2, direction.getStepX() * 0.2, 0.0, direction.getStepZ() * 0.2);
        }
    }
}
