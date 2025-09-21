package committee.nova.mods.avaritiadelight.registry;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import committee.nova.mods.avaritiadelight.screen.handler.CropExtractorScreenHandler;
import committee.nova.mods.avaritiadelight.screen.handler.ExtremeCookingPotScreenHandler;
import committee.nova.mods.avaritiadelight.screen.handler.InfinityCabinetScreenHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class ADScreenHandlers {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, AvaritiaDelight.MOD_ID);

    public static final RegistryObject<MenuType<ExtremeCookingPotScreenHandler>> EXTREME_COOKING_POT = register("extreme_cooking_pot", () -> IForgeMenuType.create(ExtremeCookingPotScreenHandler::new));
    public static final RegistryObject<MenuType<InfinityCabinetScreenHandler>> INFINITY_CABINET = register("infinity_cabinet", () -> new MenuType<>(InfinityCabinetScreenHandler::new, FeatureFlagSet.of(FeatureFlags.VANILLA)));
    public static final RegistryObject<MenuType<CropExtractorScreenHandler>> CROP_EXTRACTOR = register("crop_extractor", () -> new MenuType<>(CropExtractorScreenHandler::new, FeatureFlagSet.of(FeatureFlags.VANILLA)));

    public static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String id, Supplier<MenuType<T>> supplier) {
        return REGISTRY.register(id, supplier);
    }
}
