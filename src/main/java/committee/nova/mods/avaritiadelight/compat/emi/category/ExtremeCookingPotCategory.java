package committee.nova.mods.avaritiadelight.compat.emi.category;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.recipe.ExtremeCookingPotRecipe;
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
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record ExtremeCookingPotCategory(ExtremeCookingPotRecipe recipe) implements EmiRecipe {
    public static final EmiStack WORKSTATION = EmiStack.of(ADBlocks.EXTREME_COOKING_POT.get());
    public static final EmiRecipeCategory CATEGORY = new EmiRecipeCategory(ExtremeCookingPotRecipe.ID, WORKSTATION);
    private static final EmiTexture TEXTURE = new EmiTexture(ResourceLocation.tryBuild(AvaritiaDelight.MOD_ID, "textures/gui/jei/extreme_cooking_pot.png"), 0, 0, 189, 162);

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
        return this.recipe.getIngredients().stream().map(EmiIngredient::of).toList();
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(EmiStack.of(this.recipe.getResultItem(null)));
    }

    @Override
    public int getDisplayWidth() {
        return 191;
    }

    @Override
    public int getDisplayHeight() {
        return 164;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        ClientLevel level = Minecraft.getInstance().level;
        assert level != null;
        NonNullList<Ingredient> inputs = this.recipe.getIngredients();
        ItemStack output = this.recipe.getResultItem(level.registryAccess());
        widgets.addTexture(TEXTURE, 1, 1);
        int surroundWidth = (9 - this.recipe.width()) / 2, surroundHeight = (9 - this.recipe.height()) / 2;
        for (int i = 0; i < this.recipe.height(); i++) {
            for (int j = 0; j < this.recipe.width(); j++) {
                int index = j + (i * this.recipe.width());
                if (index < inputs.size())
                    widgets.addSlot(EmiIngredient.of(inputs.get(index)), (j + surroundWidth) * 18 + 2, (i + surroundHeight) * 18 + 2).drawBack(false);
            }
        }
        widgets.addSlot(EmiStack.of(output), 168, 76).recipeContext(this).drawBack(false);
        widgets.addSlot(EmiStack.of(this.recipe.getOutputContainer()), 168, 99).recipeContext(this).drawBack(false);
        widgets.addSlot(EmiStack.of(output), 168, 137).recipeContext(this).drawBack(false);
        if (this.recipe.shapeless()) widgets.addTexture(EmiTexture.SHAPELESS, 168, 10);
    }
}
