package committee.nova.mods.avaritiadelight.fabric;

import committee.nova.mods.avaritiadelight.AvaritiaDelightClient;
import net.fabricmc.api.ClientModInitializer;

public final class AvaritiaDelightFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AvaritiaDelightClient.process();
    }
}
