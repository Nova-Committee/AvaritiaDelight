package committee.nova.mods.avaritiadelight.recipe;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public interface ExtremeCookingPotRecipe extends Recipe<Container> {
    ResourceLocation ID = ResourceLocation.tryBuild(AvaritiaDelight.MOD_ID, "extreme_cooking");

    ItemStack getOutputContainer();

    int getCookTime();

    boolean shapeless();

    int width();

    int height();
}
