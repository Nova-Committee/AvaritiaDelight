package committee.nova.mods.avaritiadelight.registry;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.recipe.CropExtractorRecipe;
import committee.nova.mods.avaritiadelight.recipe.ExtremeCookingPotShapedRecipe;
import committee.nova.mods.avaritiadelight.recipe.ExtremeCookingPotShapelessRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;

@SuppressWarnings("DataFlowIssue")
public final class ADRecipes {
    public static final DeferredRegister<RecipeType<?>> TYPE_REGISTRY = DeferredRegister.create(Registries.RECIPE_TYPE, AvaritiaDelight.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTRY = DeferredRegister.create(Registries.RECIPE_SERIALIZER, AvaritiaDelight.MOD_ID);

    static {
        TYPE_REGISTRY.register(ExtremeCookingPotShapedRecipe.ID.getPath(), () -> ExtremeCookingPotShapedRecipe.Type.INSTANCE);
        SERIALIZER_REGISTRY.register(ExtremeCookingPotShapedRecipe.ID.getPath(), () -> ExtremeCookingPotShapedRecipe.Serializer.INSTANCE);

        TYPE_REGISTRY.register(ExtremeCookingPotShapelessRecipe.ID.getPath(), () -> ExtremeCookingPotShapelessRecipe.Type.INSTANCE);
        SERIALIZER_REGISTRY.register(ExtremeCookingPotShapelessRecipe.ID.getPath(), () -> ExtremeCookingPotShapelessRecipe.Serializer.INSTANCE);

        TYPE_REGISTRY.register(CropExtractorRecipe.ID.getPath(), () -> CropExtractorRecipe.Type.INSTANCE);
        SERIALIZER_REGISTRY.register(CropExtractorRecipe.ID.getPath(), () -> CropExtractorRecipe.Serializer.INSTANCE);
    }
}
