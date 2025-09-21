package committee.nova.mods.avaritiadelight.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.registry.ADBlocks;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ExtremeCookingPotShapelessRecipe implements ExtremeCookingPotRecipe {
    public static final ResourceLocation ID = ResourceLocation.tryBuild(AvaritiaDelight.MOD_ID, "extreme_cooking_shapeless");
    private final ResourceLocation id;
    private final String group;
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ItemStack container;
    private final int cookTime;

    public ExtremeCookingPotShapelessRecipe(ResourceLocation id, String group, NonNullList<Ingredient> inputItems, ItemStack output, ItemStack container, int cookTime) {
        this.id = id;
        this.group = group;
        this.inputItems = inputItems;
        this.output = output;
        if (!container.isEmpty())
            this.container = container;
        else if (output.getItem().getCraftingRemainingItem() != null)
            this.container = output.getItem().getCraftingRemainingItem().getDefaultInstance();
        else
            this.container = ItemStack.EMPTY;
        this.cookTime = cookTime;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @NotNull String getGroup() {
        return this.group;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess access) {
        return this.output;
    }

    @Override
    public ItemStack getOutputContainer() {
        return this.container;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull Container inv, @NotNull RegistryAccess access) {
        return this.output.copy();
    }

    @Override
    public int getCookTime() {
        return this.cookTime;
    }

    @Override
    public boolean shapeless() {
        return true;
    }

    @Override
    public int width() {
        return 9;
    }

    @Override
    public int height() {
        return 9;
    }

    @Override
    public boolean matches(@NotNull Container inv, @NotNull Level level) {
        StackedContents stackedContents = new StackedContents();
        int i = 0;

        for (int j = 0; j < 81; ++j) {
            ItemStack itemstack = inv.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                stackedContents.accountStack(itemstack, 1);
            }
        }

        return i == this.inputItems.size() && stackedContents.canCraft(this, null);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.inputItems.size();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(ADBlocks.EXTREME_COOKING_POT.get());
    }

    public enum Type implements RecipeType<ExtremeCookingPotShapelessRecipe> {
        INSTANCE
    }

    public enum Serializer implements RecipeSerializer<ExtremeCookingPotShapelessRecipe> {
        INSTANCE;

        @Override
        public @NotNull ExtremeCookingPotShapelessRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
            String groupIn = GsonHelper.getAsString(json, "group", "");
            NonNullList<Ingredient> inputItemsIn = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (inputItemsIn.isEmpty()) throw new JsonParseException("No ingredients for cooking recipe");
            else if (inputItemsIn.size() > 81)
                throw new JsonParseException("Too many ingredients for cooking recipe! The max is 81");
            else {
                ItemStack outputIn = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
                ItemStack container = GsonHelper.isValidNode(json, "container") ? ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "container")) : ItemStack.EMPTY;
                int cookTimeIn = GsonHelper.getAsInt(json, "cookingtime", 200);
                return new ExtremeCookingPotShapelessRecipe(recipeId, groupIn, inputItemsIn, outputIn, container, cookTimeIn);
            }
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
            NonNullList<Ingredient> ingredients = NonNullList.create();
            for (int i = 0; i < ingredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
                if (!ingredient.isEmpty()) ingredients.add(ingredient);
            }
            return ingredients;
        }

        @Override
        public @NotNull ExtremeCookingPotShapelessRecipe fromNetwork(@NotNull ResourceLocation recipeId, FriendlyByteBuf buf) {
            String groupIn = buf.readUtf();
            NonNullList<Ingredient> inputItemsIn = NonNullList.withSize(buf.readVarInt(), Ingredient.EMPTY);
            inputItemsIn.replaceAll(ignored -> Ingredient.fromNetwork(buf));
            ItemStack outputIn = buf.readItem();
            ItemStack container = buf.readItem();
            int cookTimeIn = buf.readVarInt();
            return new ExtremeCookingPotShapelessRecipe(recipeId, groupIn, inputItemsIn, outputIn, container, cookTimeIn);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ExtremeCookingPotShapelessRecipe recipe) {
            buf.writeUtf(recipe.group);
            buf.writeVarInt(recipe.inputItems.size());
            for (Ingredient ingredient : recipe.inputItems) ingredient.toNetwork(buf);
            buf.writeItem(recipe.output);
            buf.writeItem(recipe.container);
            buf.writeVarInt(recipe.cookTime);
        }
    }
}
