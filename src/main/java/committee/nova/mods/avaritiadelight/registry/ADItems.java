package committee.nova.mods.avaritiadelight.registry;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class ADItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(Registries.ITEM, AvaritiaDelight.MOD_ID);

    public static final RegistryObject<Item> BLAZE_KNIFE = register("blaze_knife", () -> new AvaritiaDelightKnifeItem(Tiers.GOLD, new Item.Properties().durability(4888)));
    public static final RegistryObject<Item> CRYSTAL_KNIFE = register("crystal_knife", () -> new AvaritiaDelightKnifeItem(Tiers.DIAMOND, new Item.Properties().durability(8888)));
    public static final RegistryObject<Item> NEUTRONIUM_KNIFE = register("neutronium_knife", () -> new AvaritiaDelightKnifeItem(Tiers.NETHERITE, new Item.Properties().durability(88888)));
    public static final RegistryObject<Item> INFINITY_KNIFE = register("infinity_knife", () -> new AvaritiaDelightKnifeItem(ADToolMaterials.INFINITY_KNIFE, new Item.Properties().durability(888888)));

    public static final RegistryObject<Item> BLAZE_TOMATO_SEEDS = register("blaze_tomato_seeds", () -> new ItemNameBlockItem(ADBlocks.BUDDING_BLAZE_TOMATO.get(), new Item.Properties()));
    public static final RegistryObject<Item> CRYSTAL_CABBAGE_SEEDS = register("crystal_cabbage_seeds", () -> new ItemNameBlockItem(ADBlocks.CRYSTAL_CABBAGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> NEUTRONIUM_WHEAT_SEEDS = register("neutronium_wheat_seeds", () -> new ItemNameBlockItem(ADBlocks.NEUTRONIUM_WHEAT.get(), new Item.Properties()));

    public static final RegistryObject<Item> BLAZE_TOMATO = register("blaze_tomato", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.6F).build())));
    public static final RegistryObject<Item> CRYSTAL_CABBAGE = register("crystal_cabbage", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.6F).build())));
    public static final RegistryObject<Item> NEUTRONIUM_WHEAT = register("neutronium_wheat", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BLAZE_TOMATO_SAUCE = register("blaze_tomato_sauce", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(16).saturationMod(1.2F).build())));
    public static final RegistryObject<Item> COSMIC_BEEF = register("cosmic_beef", () -> new Item(new Item.Properties().food(Foods.COOKED_CHICKEN)));
    public static final RegistryObject<Item> COSMIC_BEEF_COOKED = register("cosmic_beef_cooked", () -> new Item(new Item.Properties().food(Foods.BEEF)));
    public static final RegistryObject<Item> CRYSTAL_CABBAGE_LEAF = register("crystal_cabbage_leaf", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3F).build())));
    public static final RegistryObject<Item> RAW_CRYSTAL_PASTA = register("raw_crystal_pasta", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_LATTICE_FRIES = register("diamond_lattice_fries", () -> new Item(new Item.Properties().food(Foods.BAKED_POTATO)));
    public static final RegistryObject<Item> INFINITY_APPLE = register("infinity_apple", InfinityAppleItem::new);
    public static final RegistryObject<Item> INFINITY_LARGE_HAMBURGER = register("infinity_large_hamburger", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFINITY_TACO = register("infinity_taco", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NEUTRONIUM_POT = register("neutronium_pot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NEUTRONIUM_WHEAT_DOUGH = register("neutronium_wheat_dough", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PASTA_WITH_COSMIC_MEATBALLS = register("pasta_with_cosmic_meatballs", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SLICE_OF_ENDLESS_CAKE = register("slice_of_endless_cake", SliceOfEndlessCakeItem::new);
    public static final RegistryObject<Item> NEUTRONIUM_BOWL = register("neutronium_bowl", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NEUTRONIUM_BREAD = register("neutronium_bread", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ENDEST_EGG = register("endest_egg", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENDEST_EGG_SANDWICH = register("endest_egg_sandwich", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENDEST_FRIED_EGG = register("endest_fried_egg", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SLICE_OF_ENDEST_PIE = register("slice_of_endest_pie", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SLICE_OF_STAR_PIE = register("slice_of_star_pie", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STAR_PIE_CRUST = register("star_pie_crust", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EXPERIENCE_JELLY = register("experience_jelly", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFINITY_CATALYST_COOKIE = register("infinity_catalyst_cookie", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFINITY_FLOWERS_TEA = register("infinity_flowers_tea", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFINITY_FRIES = register("infinity_fries", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFINITY_POPSICLE = register("infinity_popsicle", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INFINITY_SALAD = register("infinity_salad", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RECORD_FRAGMENT_COOKIE = register("record_fragment_cookie", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ULTIMATE_GOURMET_STEW = register("ultimate_gourmet_stew", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FURIOUS_COCKTAIL = register("furious_cocktail", FuriousCocktailItem::new);
    public static final RegistryObject<Item> HOW_DID_WE_GET_HERE = register("how_did_we_get_here", HowDidWeGetHereItem::new);
    public static final RegistryObject<Item> INFINITY_MILK = register("infinity_milk", InfinityMilkItem::new);

    public static <T extends Item> RegistryObject<T> register(String id, Supplier<T> supplier) {
        RegistryObject<T> r = REGISTRY.register(id, supplier);
        ADItemGroups.ITEMS.add(r);
        return r;
    }
}
