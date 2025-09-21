package committee.nova.mods.avaritiadelight.mixin;

import com.google.gson.JsonObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;

@Mixin(ShapedRecipe.class)
public interface ShapedRecipeAccessor {
    @Invoker("keyFromJson")
    static Map<String, Ingredient> avaritia_delight$keyFromJson(JsonObject json) {
        throw new AssertionError("This method should be replaced by Mixin.");
    }

    @Invoker("shrink")
    static String[] avaritia_delight$shrink(String... pattern) {
        throw new AssertionError("This method should be replaced by Mixin.");
    }

    @Invoker("dissolvePattern")
    static NonNullList<Ingredient> avaritia_delight$dissolvePattern(String[] pattern, Map<String, Ingredient> symbols, int width, int height) {
        throw new AssertionError("This method should be replaced by Mixin.");
    }
}
