package me.m56738.easyarmorstands.platform.modded.dialog;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.server.dialog.body.DialogBody;

record ModdedDialogBodyImpl(ModdedPlatform platform, DialogBody body) implements ModdedDialogBody {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public DialogBody getNative() {
        return body;
    }
}
