package committee.nova.mods.avaritiadelight.registry;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.item.block.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HayBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.BasketBlock;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.block.SkilletBlock;

import java.util.function.Function;
import java.util.function.Supplier;

public final class ADBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(Registries.BLOCK, AvaritiaDelight.MOD_ID);

    public static final RegistryObject<Block> DIAMOND_LATTICE_POTATO = register("diamond_lattice_potato", AvaritiaDelightCropBlock::new, block -> new BlockItem(block, new Item.Properties().food(Foods.BAKED_POTATO)));
    public static final RegistryObject<Block> BLAZE_TOMATO = registerNoItem("blaze_tomato", BlazeTomatoBlock::new);
    public static final RegistryObject<Block> BUDDING_BLAZE_TOMATO = registerNoItem("budding_blaze_tomato", BuddingBlazeTomatoBlock::new);
    public static final RegistryObject<Block> CRYSTAL_CABBAGE = registerNoItem("crystal_cabbage", () -> new AvaritiaDelightCropBlock(ADItems.CRYSTAL_CABBAGE_SEEDS));
    public static final RegistryObject<Block> NEUTRONIUM_WHEAT = registerNoItem("neutronium_wheat", () -> new AvaritiaDelightCropBlock(ADItems.NEUTRONIUM_WHEAT_SEEDS));
    public static final RegistryObject<Block> EXTREME_COOKING_POT = register("extreme_cooking_pot", ExtremeCookingPotBlock::new);
    public static final RegistryObject<Block> EXTREME_STOVE = register("extreme_stove", () -> new ExtremeStoveBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> INFINITY_CABINET = register("infinity_cabinet", () -> new InfinityCabinetBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> NEUTRONIUM_HAY_BALE = register("neutronium_hay_bale", () -> new HayBlock(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));
    public static final RegistryObject<Block> SOUL_RICH_SOIL = register("soul_rich_soil", () -> new SoulRichSoilBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)));
    public static final RegistryObject<Block> SOUL_RICH_SOIL_FARMLAND = register("soul_rich_soil_farmland", () -> new SoilRichSoilFarmlandBlock(BlockBehaviour.Properties.copy(Blocks.FARMLAND)));
    public static final RegistryObject<Block> MOBS_STEW = register("mobs_stew", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CAULDRON).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ENDEST_PIE = register("endest_pie", () -> new PieBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), ADItems.SLICE_OF_ENDEST_PIE));
    public static final RegistryObject<Block> STAR_PIE = register("star_pie", () -> new PieBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), ADItems.SLICE_OF_STAR_PIE));

    public static final RegistryObject<Block> EXTREME_SKILLET = register("extreme_skillet", () -> new SkilletBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(0.5F, 6.0F).sound(SoundType.LANTERN)));
    public static final RegistryObject<Block> INFINITY_BASKET = register("infinity_basket", () -> new BasketBlock(BlockBehaviour.Properties.of().strength(1.5F).sound(SoundType.BAMBOO_WOOD)));
    public static final RegistryObject<Block> CROP_EXTRACTOR = register("crop_extractor", () -> new CropExtractorBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> BLAZE_TOMATO_CRATE = register("blaze_tomato_crate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CRYSTAL_CABBAGE_CRATE = register("crystal_cabbage_crate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DIAMOND_LATTICE_POTATO_CRATE = register("diamond_lattice_potato_crate", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static <T extends Block> RegistryObject<T> register(String id, Supplier<T> supplier) {
        return register(id, supplier, block -> new BlockItem(block, new Item.Properties()));
    }

    public static <T extends Block> RegistryObject<T> register(String id, Supplier<T> supplier, Function<Block, Item> item) {
        final RegistryObject<T> r = registerNoItem(id, supplier);
        ADItems.register(id, () -> item.apply(r.get()));
        return r;
    }

    public static <T extends Block> RegistryObject<T> registerNoItem(String id, Supplier<T> supplier) {
        return REGISTRY.register(id, supplier);
    }
}
