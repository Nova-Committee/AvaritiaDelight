package committee.nova.mods.avaritiadelight.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CropExtractorRecipe implements Recipe<Container> {
    public static final ResourceLocation ID = ResourceLocation.tryBuild(AvaritiaDelight.MOD_ID, "crop_extractor");
    private final ResourceLocation id;
    private final Ingredient input;
    private final List<ItemStack> outputs;
    private final int time;

    public CropExtractorRecipe(ResourceLocation id, Ingredient input, List<ItemStack> outputs, int time) {
        this.id = id;
        this.input = input;
        this.outputs = outputs;
        this.time = time;
    }

    @Override
    public boolean matches(Container inventory, @NotNull Level world) {
        return this.input.test(inventory.getItem(0));
    }

    @Deprecated
    @Override
    public @NotNull ItemStack assemble(@NotNull Container inventory, @NotNull RegistryAccess registryManager) {
        return ItemStack.EMPTY;
    }

    public Ingredient getInput() {
        return this.input;
    }

    public List<ItemStack> getOutputs() {
        return this.outputs;
    }

    public int getTime() {
        return this.time;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height > 0;
    }

    @Deprecated
    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryManager) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public enum Type implements RecipeType<CropExtractorRecipe> {
        INSTANCE
    }

    public enum Serializer implements RecipeSerializer<CropExtractorRecipe> {
        INSTANCE;

        @Override
        public @NotNull CropExtractorRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            List<ItemStack> outputs = GsonHelper.getAsJsonArray(json, "results").asList().stream().map(JsonElement::getAsJsonObject).map(ShapedRecipe::itemStackFromJson).toList();
            int time = GsonHelper.getAsInt(json, "time", 200);
            return new CropExtractorRecipe(id, input, outputs, time);
        }

        @Override
        public CropExtractorRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            Ingredient input = Ingredient.fromNetwork(buf);
            NonNullList<ItemStack> outputs = NonNullList.withSize(buf.readVarInt(), ItemStack.EMPTY);
            outputs.replaceAll(ignored -> buf.readItem());
            int cookTimeIn = buf.readInt();
            return new CropExtractorRecipe(id, input, outputs, cookTimeIn);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buf, CropExtractorRecipe recipe) {
            recipe.input.toNetwork(buf);
            buf.writeInt(recipe.outputs.size());
            for (ItemStack stack : recipe.outputs) buf.writeItem(stack);
            buf.writeInt(recipe.time);
        }
    }
}
