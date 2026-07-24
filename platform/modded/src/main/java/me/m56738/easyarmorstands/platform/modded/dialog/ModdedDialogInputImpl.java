package me.m56738.easyarmorstands.platform.modded.dialog;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.server.dialog.Input;

record ModdedDialogInputImpl(ModdedPlatform platform, Input input) implements ModdedDialogInput {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Input getNative() {
        return input;
    }
}
