package committee.nova.mods.avaritiadelight.compat.jei;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.compat.jei.category.CropExtractorCategory;
import committee.nova.mods.avaritiadelight.compat.jei.category.ExtremeCookingPotCategory;
import committee.nova.mods.avaritiadelight.recipe.CropExtractorRecipe;
import committee.nova.mods.avaritiadelight.recipe.ExtremeCookingPotShapedRecipe;
import committee.nova.mods.avaritiadelight.recipe.ExtremeCookingPotShapelessRecipe;
import committee.nova.mods.avaritiadelight.registry.ADBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class ADJeiPlugin implements IModPlugin {
    @Override
    public @NotNull Identifier getPluginUid() {
        return new Identifier(AvaritiaDelight.MOD_ID, AvaritiaDelight.MOD_ID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new ExtremeCookingPotCategory<>(helper, ExtremeCookingPotCategory.SHAPED_TYPE));
        registration.addRecipeCategories(new ExtremeCookingPotCategory<>(helper, ExtremeCookingPotCategory.SHAPELESS_TYPE));
        registration.addRecipeCategories(new CropExtractorCategory(helper));
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        ClientWorld world = MinecraftClient.getInstance().world;
        assert world != null;
        RecipeManager manager = world.getRecipeManager();
        registration.addRecipes(ExtremeCookingPotCategory.SHAPED_TYPE, manager.listAllOfType(ExtremeCookingPotShapedRecipe.Type.INSTANCE));
        registration.addRecipes(ExtremeCookingPotCategory.SHAPELESS_TYPE, manager.listAllOfType(ExtremeCookingPotShapelessRecipe.Type.INSTANCE));
        registration.addRecipes(CropExtractorCategory.TYPE, manager.listAllOfType(CropExtractorRecipe.Type.INSTANCE));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ADBlocks.EXTREME_COOKING_POT.get()), ExtremeCookingPotCategory.SHAPED_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ADBlocks.CROP_EXTRACTOR.get()), CropExtractorCategory.TYPE);
    }
}
