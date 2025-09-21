package committee.nova.mods.avaritiadelight.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.mixin.ShapedRecipeAccessor;
import committee.nova.mods.avaritiadelight.registry.ADBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class ExtremeCookingPotShapedRecipe implements ExtremeCookingPotRecipe {
    public static final ResourceLocation ID = ResourceLocation.tryBuild(AvaritiaDelight.MOD_ID, "extreme_cooking_shaped");
    private final ResourceLocation id;
    private final String group;
    private final int width, height;
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ItemStack container;
    private final int cookTime;

    public ExtremeCookingPotShapedRecipe(ResourceLocation id, String group, int width, int height, NonNullList<Ingredient> inputItems, ItemStack output, ItemStack container, int cookTime) {
        this.id = id;
        this.group = group;
        this.width = width;
        this.height = height;
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
        return false;
    }

    @Override
    public int width() {
        return this.width;
    }

    @Override
    public int height() {
        return this.height;
    }

    @Override
    public boolean matches(@NotNull Container inv, @NotNull Level level) {
        for (int i = 0; i <= 9 - this.width; ++i)
            for (int j = 0; j <= 9 - this.height; ++j) {
                if (this.checkMatch(inv, i, j, true)) return true;
                if (this.checkMatch(inv, i, j, false)) return true;
            }
        return false;
    }

    private boolean checkMatch(Container inventory, int x, int y, boolean mirror) {
        for (int i = 0; i < 9; ++i)
            for (int j = 0; j < 9; ++j) {
                int k = i - x;
                int l = j - y;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.width && l < this.height)
                    ingredient = mirror ? this.inputItems.get(this.width - k - 1 + l * this.width) : this.inputItems.get(k + l * this.width);
                if (!ingredient.test(inventory.getItem(i + j * 9)))
                    return false;
            }
        return true;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.width && height >= this.height;
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

    public enum Type implements RecipeType<ExtremeCookingPotShapedRecipe> {
        INSTANCE
    }

    public enum Serializer implements RecipeSerializer<ExtremeCookingPotShapedRecipe> {
        INSTANCE;

        @Override
        public @NotNull ExtremeCookingPotShapedRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
            String group = GsonHelper.getAsString(json, "group", "");
            Map<String, Ingredient> map = ShapedRecipeAccessor.avaritia_delight$keyFromJson(GsonHelper.getAsJsonObject(json, "key"));
            String[] strings = ShapedRecipeAccessor.avaritia_delight$shrink(getPattern(GsonHelper.getAsJsonArray(json, "pattern")));
            int width = strings[0].length();
            int height = strings.length;
            NonNullList<Ingredient> inputs = ShapedRecipeAccessor.avaritia_delight$dissolvePattern(strings, map, width, height);
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            ItemStack container = GsonHelper.isValidNode(json, "container") ? ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "container")) : ItemStack.EMPTY;
            int cookTime = GsonHelper.getAsInt(json, "cookingtime", 200);
            return new ExtremeCookingPotShapedRecipe(recipeId, group, width, height, inputs, output, container, cookTime);
        }

        static String[] getPattern(JsonArray json) {
            String[] strings = new String[json.size()];
            if (strings.length > 9) throw new JsonSyntaxException("Invalid pattern: too many rows, 9 is maximum");
            else if (strings.length == 0) throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
            else {
                for (int i = 0; i < strings.length; ++i) {
                    String string = GsonHelper.convertToString(json.get(i), "pattern[" + i + "]");
                    if (string.length() > 9)
                        throw new JsonSyntaxException("Invalid pattern: too many columns, 9 is maximum");
                    if (i > 0 && strings[0].length() != string.length())
                        throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                    strings[i] = string;
                }
                return strings;
            }
        }

        @Override
        public @NotNull ExtremeCookingPotShapedRecipe fromNetwork(@NotNull ResourceLocation recipeId, FriendlyByteBuf buf) {
            String groupIn = buf.readUtf();
            int width = buf.readInt(), height = buf.readInt();
            NonNullList<Ingredient> inputItemsIn = NonNullList.withSize(width * height, Ingredient.EMPTY);
            inputItemsIn.replaceAll(ignored -> Ingredient.fromNetwork(buf));
            ItemStack outputIn = buf.readItem();
            ItemStack container = buf.readItem();
            int cookTimeIn = buf.readVarInt();
            return new ExtremeCookingPotShapedRecipe(recipeId, groupIn, width, height, inputItemsIn, outputIn, container, cookTimeIn);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ExtremeCookingPotShapedRecipe recipe) {
            buf.writeUtf(recipe.group);
            buf.writeInt(recipe.width);
            buf.writeInt(recipe.height);
            for (Ingredient ingredient : recipe.inputItems) ingredient.toNetwork(buf);
            buf.writeItem(recipe.output);
            buf.writeItem(recipe.container);
            buf.writeVarInt(recipe.cookTime);
        }
    }
}
