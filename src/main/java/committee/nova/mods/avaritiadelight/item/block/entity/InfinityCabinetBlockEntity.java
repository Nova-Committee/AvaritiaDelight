package committee.nova.mods.avaritiadelight.item.block.entity;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.registry.ADBlockEntities;
import committee.nova.mods.avaritiadelight.screen.handler.InfinityCabinetScreenHandler;
import committee.nova.mods.avaritiadelight.util.ImplementedInventory;
import committee.nova.mods.avaritiadelight.util.InventoryHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InfinityCabinetBlockEntity extends BlockEntity implements MenuProvider, ImplementedInventory, Nameable {
    private final ContainerOpenersCounter viewerCountManager;
    private final NonNullList<ItemStack> stacks = NonNullList.withSize(243, ItemStack.EMPTY);
    @Nullable
    private Component customName = null;

    public InfinityCabinetBlockEntity(BlockPos pos, BlockState state) {
        super(ADBlockEntities.INFINITY_CABINET.get(), pos, state);
        this.viewerCountManager = new ContainerOpenersCounter() {
            @Override
            protected void onOpen(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state) {
                InfinityCabinetBlockEntity.this.playSound(state, SoundEvents.BARREL_OPEN);
                InfinityCabinetBlockEntity.this.setOpen(state, true);
            }

            @Override
            protected void onClose(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state) {
                InfinityCabinetBlockEntity.this.playSound(state, SoundEvents.BARREL_CLOSE);
                InfinityCabinetBlockEntity.this.setOpen(state, false);
            }

            @Override
            protected void openerCountChanged(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, int oldViewerCount, int newViewerCount) {
            }

            @Override
            protected boolean isOwnContainer(@NotNull Player player) {
                return player.containerMenu instanceof InfinityCabinetScreenHandler handler && handler.getInventory() == InfinityCabinetBlockEntity.this;
            }
        };
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        this.stacks.clear();
        InventoryHelper.readNbt(nbt.getCompound("Items"), this.stacks);
        if (nbt.contains("CustomName")) this.customName = Component.Serializer.fromJson(nbt.getString("CustomName"));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("Items", InventoryHelper.writeNbt(new CompoundTag(), this.stacks));
        if (this.customName != null) nbt.putString("CustomName", Component.Serializer.toJson(this.customName));
    }

    @Override
    public @NotNull Component getName() {
        return this.customName;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return this.customName != null ? this.customName : Component.translatable("block.%s.infinity_cabinet".formatted(AvaritiaDelight.MOD_ID));
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new InfinityCabinetScreenHandler(syncId, this, playerInventory);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    public void setCustomName(@Nullable Component customName) {
        this.customName = customName;
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public int getMaxStackSize() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void startOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator())
            this.viewerCountManager.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
    }

    @Override
    public void stopOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator())
            this.viewerCountManager.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
    }

    public void tick() {
        if (!this.remove)
            this.viewerCountManager.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
    }

    private void setOpen(BlockState state, boolean open) {
        assert this.level != null;
        this.level.setBlock(this.getBlockPos(), state.setValue(BarrelBlock.OPEN, open), 3);
    }

    private void playSound(BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = state.getValue(BarrelBlock.FACING).getNormal();
        double d = (double) this.worldPosition.getX() + 0.5 + (double) vec3i.getX() / 2.0;
        double e = (double) this.worldPosition.getY() + 0.5 + (double) vec3i.getY() / 2.0;
        double f = (double) this.worldPosition.getZ() + 0.5 + (double) vec3i.getZ() / 2.0;
        assert this.level != null;
        this.level.playSound(null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }
}
