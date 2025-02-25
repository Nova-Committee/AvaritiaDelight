package committee.nova.mods.avaritiadelight.registry;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.item.block.AvaritiaDelightCropBlock;
import committee.nova.mods.avaritiadelight.item.block.BlazeTomatoBlock;
import committee.nova.mods.avaritiadelight.item.block.BuddingBlazeTomatoBlock;
import committee.nova.mods.avaritiadelight.item.block.ExtremeCookingPotBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.HayBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Supplier;

public final class ADBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(AvaritiaDelight.MOD_ID, RegistryKeys.BLOCK);

    public static final RegistrySupplier<Block> DIAMOND_LATTICE_POTATO = register("diamond_lattice_potato", AvaritiaDelightCropBlock::new);
    public static final RegistrySupplier<Block> BLAZE_TOMATO = registerNoItem("blaze_tomato", BlazeTomatoBlock::new);
    public static final RegistrySupplier<Block> BUDDING_BLAZE_TOMATO = registerNoItem("budding_blaze_tomato", BuddingBlazeTomatoBlock::new);
    public static final RegistrySupplier<Block> CRYSTAL_CABBAGE = registerNoItem("crystal_cabbage", () -> new AvaritiaDelightCropBlock(ADItems.CRYSTAL_CABBAGE_SEEDS));
    public static final RegistrySupplier<Block> NEUTRONIUM_WHEAT = registerNoItem("neutronium_wheat", () -> new AvaritiaDelightCropBlock(ADItems.NEUTRONIUM_WHEAT_SEEDS));
    public static final RegistrySupplier<Block> EXTREME_COOKING_POT = register("extreme_cooking_pot", ExtremeCookingPotBlock::new);

    public static final RegistrySupplier<Block> BLAZE_TOMATO_CRATE = register("blaze_tomato_crate", () -> new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
    public static final RegistrySupplier<Block> CRYSTAL_CABBAGE_CRATE = register("crystal_cabbage_crate", () -> new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));
    public static final RegistrySupplier<Block> DIAMOND_LATTICE_POTATO_CRATE = register("diamond_lattice_potato_crate", () -> new Block(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));

    public static final RegistrySupplier<Block> CROP_EXTRACTOR = register("crop_extractor", () -> new Block(AbstractBlock.Settings.create()));
    public static final RegistrySupplier<Block> NEUTRONIUM_HAY_BALE = register("neutronium_hay_bale", () -> new HayBlock(AbstractBlock.Settings.copy(Blocks.HAY_BLOCK)));

    public static <T extends Block> RegistrySupplier<T> register(String id, Supplier<T> supplier) {
        final RegistrySupplier<T> r = registerNoItem(id, supplier);
        ADItems.register(id, () -> new BlockItem(r.get(), new Item.Settings().arch$tab(ADItemGroups.MAIN)));
        return r;
    }

    public static <T extends Block> RegistrySupplier<T> registerNoItem(String id, Supplier<T> supplier) {
        return REGISTRY.register(id, supplier);
    }
}
