package committee.nova.mods.avaritiadelight.compat.emi.category;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.recipe.CropExtractorRecipe;
import committee.nova.mods.avaritiadelight.registry.ADBlocks;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record CropExtractorCategory(CropExtractorRecipe recipe) implements EmiRecipe {
    public static final EmiStack WORKSTATION = EmiStack.of(ADBlocks.CROP_EXTRACTOR.get());
    public static final EmiRecipeCategory CATEGORY = new EmiRecipeCategory(CropExtractorRecipe.ID, WORKSTATION);
    private static final EmiTexture TEXTURE = new EmiTexture(ResourceLocation.tryBuild(AvaritiaDelight.MOD_ID, "textures/gui/jei/crop_extractor.png"), 0, 0, 189, 162);

    @Override
    public EmiRecipeCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.recipe.getId();
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(EmiIngredient.of(this.recipe.getInput()));
    }

    @Override
    public List<EmiStack> getOutputs() {
        return this.recipe.getOutputs().stream().map(EmiStack::of).toList();
    }

    @Override
    public int getDisplayWidth() {
        return 169;
    }

    @Override
    public int getDisplayHeight() {
        return 62;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        ClientLevel level = Minecraft.getInstance().level;
        assert level != null;
        Ingredient input = this.recipe.getInput();
        List<ItemStack> outputs = this.recipe.getOutputs();
        widgets.addTexture(TEXTURE, 1, 1);
        widgets.addSlot(EmiIngredient.of(input), 36, 21).drawBack(false);
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                if (i * 2 + j < outputs.size())
                    widgets.addSlot(EmiStack.of(outputs.get(i * 2 + j)), j * 18 + 99, i * 18 + 12).recipeContext(this).drawBack(false);
    }
}
