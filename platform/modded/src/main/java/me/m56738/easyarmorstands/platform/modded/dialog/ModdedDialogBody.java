package me.m56738.easyarmorstands.platform.modded.dialog;

import me.m56738.easyarmorstands.platform.dialog.DialogBody;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;

public interface ModdedDialogBody extends DialogBody, ModdedPlatformHolder {
    net.minecraft.server.dialog.body.DialogBody getNative();

    static ModdedDialogBody fromNative(ModdedPlatform platform, net.minecraft.server.dialog.body.DialogBody body) {
        return new ModdedDialogBodyImpl(platform, body);
    }

    static net.minecraft.server.dialog.body.DialogBody toNative(DialogBody body) {
        return ((ModdedDialogBody) body).getNative();
    }
}
