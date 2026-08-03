package dev.erudites.mods.imewarmup.mixin.input;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.erudites.mods.imewarmup.client.ImeWarmupClientMod;
import dev.erudites.mods.imewarmup.mixin.platform.TextInputManagerAccessor;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.PreeditEvent;
import org.jspecify.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(KeyboardHandler.class)
abstract class KeyboardHandlerMixin {

    @Shadow
    private @Nullable PreeditEvent lastPreeditEvent;

    @WrapMethod(method = "preeditCallback")
    private void imewarmup$discardPreeditOutsideTextInput(
        final long handle,
        final @Nullable PreeditEvent event,
        final Operation<Void> original
    ) {
        if (handle != Minecraft.getInstance().getWindow().handle() || textInputEnabled()) {
            original.call(handle, event);
            return;
        }
        if (event != null) {
            if (ImeWarmupClientMod.DEBUG) {
                ImeWarmupClientMod.LOGGER.info("Discarding out-of-field pre-edit: \"{}\"", event.fullText());
            }
            GLFW.glfwResetPreeditText(handle);
        }
        this.lastPreeditEvent = null;
    }

    @Unique
    private static boolean textInputEnabled() {
        return ((TextInputManagerAccessor) Minecraft.getInstance().textInputManager()).imewarmup$textInputEnabled();
    }
}
