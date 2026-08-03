package dev.erudites.mods.imewarmup.fabric.client;

import dev.erudites.mods.imewarmup.client.ImeWarmupClientMod;
import net.fabricmc.api.ClientModInitializer;

public class ImeWarmupClientFabricMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ImeWarmupClientMod.initializeClient();
    }
}
