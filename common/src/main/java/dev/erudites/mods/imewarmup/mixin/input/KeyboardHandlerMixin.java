package dev.erudites.mods.imewarmup.mixin.input;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.erudites.mods.imewarmup.client.ImeWarmupClientMod;
import dev.erudites.mods.imewarmup.mixin.platform.TextInputManagerAccessor;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.PreeditEvent;
import org.jspecify.annotations.Nullable;
import org.lwjgl.sdl.SDLKeyboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(KeyboardHandler.class)
abstract class KeyboardHandlerMixin {

    @Shadow @Final
    private Minecraft minecraft;

    @Shadow
    private @Nullable PreeditEvent lastPreeditEvent;

    @WrapMethod(method = "textEditing")
    private void imewarmup$discardPreeditOutsideTextInput(
        final long handle,
        final @Nullable PreeditEvent event,
        final Operation<Void> original
    ) {
        if (this.deliverToGame(handle)) {
            original.call(handle, event);
            return;
        }
        if (event != null) {
            if (ImeWarmupClientMod.DEBUG) {
                ImeWarmupClientMod.LOGGER.info("Discarding out-of-field pre-edit: \"{}\"", event.fullText());
            }
            SDLKeyboard.SDL_ClearComposition(handle);
        }
        this.forgetPreeditState();
    }

    @WrapMethod(method = "textInput")
    private void imewarmup$discardTextOutsideTextInput(
        final long handle,
        final String text,
        final Operation<Void> original
    ) {
        if (this.deliverToGame(handle)) {
            original.call(handle, text);
            return;
        }
        if (ImeWarmupClientMod.DEBUG) {
            ImeWarmupClientMod.LOGGER.info("Discarding out-of-field text: \"{}\"", text);
        }
        this.forgetPreeditState();
    }

    @Unique
    private void forgetPreeditState() {
        this.lastPreeditEvent = null;
    }

    @Unique
    private boolean deliverToGame(final long handle) {
        return (handle != 0L && handle != this.minecraft.getWindow().handle())
            || ((TextInputManagerAccessor) this.minecraft.textInputManager()).imewarmup$textInputEnabled();
    }
}
