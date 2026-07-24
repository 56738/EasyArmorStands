package me.m56738.easyarmorstands.platform.modded.dialog;

import me.m56738.easyarmorstands.platform.dialog.Dialog;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;

public interface ModdedDialog extends Dialog, ModdedPlatformHolder {
    net.minecraft.server.dialog.Dialog getNative();

    static ModdedDialog fromNative(ModdedPlatform platform, net.minecraft.server.dialog.Dialog dialog) {
        return new ModdedDialogImpl(platform, dialog);
    }

    static net.minecraft.server.dialog.Dialog toNative(Dialog dialog) {
        return ((ModdedDialog) dialog).getNative();
    }
}
