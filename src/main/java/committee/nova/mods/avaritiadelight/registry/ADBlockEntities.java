package committee.nova.mods.avaritiadelight.registry;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.item.block.entity.CropExtractorBlockEntity;
import committee.nova.mods.avaritiadelight.item.block.entity.ExtremeCookingPotBlockEntity;
import committee.nova.mods.avaritiadelight.item.block.entity.ExtremeStoveBlockEntity;
import committee.nova.mods.avaritiadelight.item.block.entity.InfinityCabinetBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@SuppressWarnings("DataFlowIssue")
public final class ADBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, AvaritiaDelight.MOD_ID);

    public static final RegistryObject<BlockEntityType<ExtremeCookingPotBlockEntity>> EXTREME_COOKING_POT = register("extreme_cooking_pot", () -> BlockEntityType.Builder.of(ExtremeCookingPotBlockEntity::new, ADBlocks.EXTREME_COOKING_POT.get()).build(null));
    public static final RegistryObject<BlockEntityType<ExtremeStoveBlockEntity>> EXTREME_STOVE = register("extreme_stove", () -> BlockEntityType.Builder.of(ExtremeStoveBlockEntity::new, ADBlocks.EXTREME_STOVE.get()).build(null));
    public static final RegistryObject<BlockEntityType<InfinityCabinetBlockEntity>> INFINITY_CABINET = register("infinity_cabinet", () -> BlockEntityType.Builder.of(InfinityCabinetBlockEntity::new, ADBlocks.INFINITY_CABINET.get()).build(null));
    public static final RegistryObject<BlockEntityType<CropExtractorBlockEntity>> CROP_EXTRACTOR = register("crop_extractor", () -> BlockEntityType.Builder.of(CropExtractorBlockEntity::new, ADBlocks.CROP_EXTRACTOR.get()).build(null));

    public static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String id, Supplier<BlockEntityType<T>> supplier) {
        return REGISTRY.register(id, supplier);
    }
}
