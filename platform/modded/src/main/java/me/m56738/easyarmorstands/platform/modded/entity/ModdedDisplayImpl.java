package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.entity.Display;

record ModdedDisplayImpl(ModdedPlatform platform, Display entity) implements ModdedDisplay {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Display getNative() {
        return entity;
    }
}
