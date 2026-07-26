package me.m56738.easyarmorstands.platform.modded.dialog;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.nbt.Tag;

record ModdedDialogResponseViewImpl(ModdedPlatform platform, Tag tag) implements ModdedDialogResponseView {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Tag getNative() {
        return tag;
    }
}
