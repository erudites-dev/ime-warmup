package dev.erudites.mods.imewarmup.mixin.platform;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.TextInputManager;
import dev.erudites.mods.imewarmup.client.ImeWarmupClientMod;
import org.lwjgl.sdl.SDLKeyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TextInputManager.class)
abstract class TextInputManagerMixin {

    @WrapOperation(
        method = "stopTextInput()V",
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/sdl/SDLKeyboard;SDL_StopTextInput(J)Z"
        )
    )
    private boolean imewarmup$keepTextInputActive(final long owner, final Operation<Boolean> original) {
        return true;
    }

    @WrapOperation(
        method = "startTextInput",
        at = @At(
            value = "INVOKE",
            target = "Lorg/lwjgl/sdl/SDLKeyboard;SDL_StartTextInput(J)Z"
        )
    )
    private boolean imewarmup$skipRedundantStart(final long owner, final Operation<Boolean> original) {
        if (SDLKeyboard.SDL_TextInputActive(owner)) {
            return true;
        }
        if (ImeWarmupClientMod.DEBUG) {
            ImeWarmupClientMod.LOGGER.info("Starting text input");
        }
        return original.call(owner);
    }
}
