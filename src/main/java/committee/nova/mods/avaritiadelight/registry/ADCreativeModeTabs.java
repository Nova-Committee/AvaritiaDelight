package committee.nova.mods.avaritiadelight.registry;

import committee.nova.mods.avaritiadelight.AvaritiaDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public final class ADCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AvaritiaDelight.MOD_ID);

    public static final List<RegistryObject<? extends Item>> ITEMS = new LinkedList<>();

    public static final RegistryObject<CreativeModeTab> MAIN = register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.%s.main".formatted(AvaritiaDelight.MOD_ID)))
            .icon(() -> new ItemStack(ADItems.INFINITY_APPLE.get()))
            .displayItems((params, output) -> ITEMS.stream().map(Supplier::get).map(Item::getDefaultInstance).forEach(output::accept))
            .build());

    public static RegistryObject<CreativeModeTab> register(String id, Supplier<CreativeModeTab> supplier) {
        return REGISTRY.register(id, supplier);
    }
}
