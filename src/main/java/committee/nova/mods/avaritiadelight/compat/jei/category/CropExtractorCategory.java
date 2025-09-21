package committee.nova.mods.avaritiadelight.compat.jei.category;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.recipe.CropExtractorRecipe;
import committee.nova.mods.avaritiadelight.registry.ADBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CropExtractorCategory implements IRecipeCategory<CropExtractorRecipe> {
    public static final RecipeType<CropExtractorRecipe> TYPE = new RecipeType<>(CropExtractorRecipe.ID, CropExtractorRecipe.class);
    private static final ResourceLocation TEXTURE = ResourceLocation.tryBuild(AvaritiaDelight.MOD_ID, "textures/gui/jei/crop_extractor.png");
    private final IDrawable background, icon;

    public CropExtractorCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 170, 63);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ADBlocks.CROP_EXTRACTOR.get()));
    }

    @Override
    public @NotNull RecipeType<CropExtractorRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable(CropExtractorRecipe.ID.toLanguageKey("jei.category"));
    }

    @SuppressWarnings("removal")
    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, CropExtractorRecipe recipe, @NotNull IFocusGroup focuses) {
        ClientLevel world = Minecraft.getInstance().level;
        assert world != null;
        Ingredient input = recipe.getInput();
        List<ItemStack> outputs = recipe.getOutputs();
        builder.addSlot(RecipeIngredientRole.INPUT, 36, 21).addIngredients(input);
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                if (i * 2 + j < outputs.size())
                    builder.addSlot(RecipeIngredientRole.OUTPUT, j * 18 + 99, i * 18 + 12).addItemStack(outputs.get(i * 2 + j));
        builder.moveRecipeTransferButton(170, 30);
    }
}
