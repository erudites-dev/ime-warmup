package dev.erudites.mods.imewarmup.mixin.platform;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.platform.TextInputManager;
import com.mojang.blaze3d.platform.Window;
import dev.erudites.mods.imewarmup.client.ImeWarmupClientMod;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TextInputManager.class)
abstract class TextInputManagerMixin {

    @Shadow @Final
    private Window window;

    @WrapMethod(method = "tickOutsideTextInput")
    private void imewarmup$keepImeWarm(final Operation<Void> original) {}

    @WrapMethod(method = "setIMEInputMode")
    private void imewarmup$skipRedundantImeInputMode(final boolean value, final Operation<Void> original) {
        int requested = value ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE;
        if (GLFW.glfwGetInputMode(this.window.handle(), GLFW.GLFW_IME) == requested) {
            return;
        }
        if (ImeWarmupClientMod.DEBUG) {
            ImeWarmupClientMod.LOGGER.info("Ime input mode -> {}", value);
        }
        original.call(value);
    }
}
