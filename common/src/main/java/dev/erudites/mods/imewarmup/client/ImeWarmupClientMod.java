package dev.erudites.mods.imewarmup.client;

import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ImeWarmupClientMod {

    public static final String MODID = "imewarmup";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    /**
     * Enable with {@code -Dimewarmup.debug=true} to trace Ime activations and discarded compositions.
     */
    public static final boolean DEBUG = Boolean.getBoolean(MODID + ".debug");

    private ImeWarmupClientMod() {}

    public static void initializeClient() {
        if (Util.getPlatform() != Util.OS.OSX) {
            LOGGER.info("Not running on macOS, Ime Warmup stays inactive");
        }
    }
}
