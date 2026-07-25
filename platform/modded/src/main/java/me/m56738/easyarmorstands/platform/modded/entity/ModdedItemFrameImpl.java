package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.entity.decoration.ItemFrame;

record ModdedItemFrameImpl(ModdedPlatform platform, ItemFrame entity) implements ModdedItemFrame {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public ItemFrame getNative() {
        return entity;
    }
}
