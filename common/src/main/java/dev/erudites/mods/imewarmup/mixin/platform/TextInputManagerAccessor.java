package dev.erudites.mods.imewarmup.mixin.platform;

import com.mojang.blaze3d.platform.TextInputManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TextInputManager.class)
public interface TextInputManagerAccessor {

    @Accessor("textInputEnabled")
    boolean imewarmup$textInputEnabled();
}
