package dev.erudites.mods.imewarmup.neoforge.client;

import dev.erudites.mods.imewarmup.client.ImeWarmupClientMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = ImeWarmupClientMod.MODID, dist = Dist.CLIENT)
public class ImeWarmupClientNeoForgeMod {

    public ImeWarmupClientNeoForgeMod(IEventBus modBus) {
        ImeWarmupClientMod.initializeClient();
    }
}
