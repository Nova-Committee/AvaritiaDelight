package committee.nova.mods.avaritiadelight.registry;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.item.block.AvaritiaDelightCropBlock;
import committee.nova.mods.avaritiadelight.item.block.BlazeTomatoBlock;
import committee.nova.mods.avaritiadelight.item.block.BuddingBlazeTomatoBlock;
import committee.nova.mods.avaritiadelight.item.block.ExtremeCookingPotBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Supplier;

public final class ADBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(AvaritiaDelight.MOD_ID, RegistryKeys.BLOCK);

    public static final RegistrySupplier<Block> BLAZE_TOMATO = register("blaze_tomato", BlazeTomatoBlock::new);
    public static final RegistrySupplier<Block> BUDDING_BLAZE_TOMATO = register("budding_blaze_tomato", BuddingBlazeTomatoBlock::new);
    public static final RegistrySupplier<Block> CRYSTAL_CABBAGE = register("crystal_cabbage", AvaritiaDelightCropBlock::new);
    public static final RegistrySupplier<Block> DIAMOND_LATTICE_POTATO = register("diamond_lattice_potato", AvaritiaDelightCropBlock::new);
    public static final RegistrySupplier<Block> NEUTRONIUM_WHEAT = register("neutronium_wheat", AvaritiaDelightCropBlock::new);
    public static final RegistrySupplier<Block> EXTREME_COOKING_POT = register("extreme_cooking_pot", ExtremeCookingPotBlock::new);

    public static <T extends Block> RegistrySupplier<T> register(String id, Supplier<T> supplier) {
        final RegistrySupplier<T> r = REGISTRY.register(id, supplier);
        ADItems.register(id, () -> new BlockItem(r.get(), new Item.Settings().arch$tab(ADItemGroups.MAIN)));
        return r;
    }
}
