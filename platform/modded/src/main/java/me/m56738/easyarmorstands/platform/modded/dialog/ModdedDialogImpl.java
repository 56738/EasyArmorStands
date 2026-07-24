package me.m56738.easyarmorstands.platform.modded.dialog;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.server.dialog.Dialog;

record ModdedDialogImpl(ModdedPlatform platform, Dialog dialog) implements ModdedDialog {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Dialog getNative() {
        return dialog;
    }
}
