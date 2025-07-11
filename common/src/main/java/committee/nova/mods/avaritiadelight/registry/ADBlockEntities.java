package committee.nova.mods.avaritiadelight.registry;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.item.block.entity.CropExtractorBlockEntity;
import committee.nova.mods.avaritiadelight.item.block.entity.ExtremeCookingPotBlockEntity;
import committee.nova.mods.avaritiadelight.item.block.entity.ExtremeStoveBlockEntity;
import committee.nova.mods.avaritiadelight.item.block.entity.InfinityCabinetBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Supplier;

public final class ADBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(AvaritiaDelight.MOD_ID, RegistryKeys.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<ExtremeCookingPotBlockEntity>> EXTREME_COOKING_POT = register("extreme_cooking_pot", () -> BlockEntityType.Builder.create(ExtremeCookingPotBlockEntity::new, ADBlocks.EXTREME_COOKING_POT.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<ExtremeStoveBlockEntity>> EXTREME_STOVE = register("extreme_stove", () -> BlockEntityType.Builder.create(ExtremeStoveBlockEntity::new, ADBlocks.EXTREME_STOVE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<InfinityCabinetBlockEntity>> INFINITY_CABINET = register("infinity_cabinet", () -> BlockEntityType.Builder.create(InfinityCabinetBlockEntity::new, ADBlocks.INFINITY_CABINET.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CropExtractorBlockEntity>> CROP_EXTRACTOR = register("crop_extractor", () -> BlockEntityType.Builder.create(CropExtractorBlockEntity::new, ADBlocks.CROP_EXTRACTOR.get()).build(null));

    public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(String id, Supplier<BlockEntityType<T>> supplier) {
        return REGISTRY.register(id, supplier);
    }
}
