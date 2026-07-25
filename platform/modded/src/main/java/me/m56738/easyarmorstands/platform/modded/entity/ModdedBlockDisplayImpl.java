package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.entity.Display;

record ModdedBlockDisplayImpl(ModdedPlatform platform, Display.BlockDisplay entity) implements ModdedBlockDisplay {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Display.BlockDisplay getNative() {
        return entity;
    }
}
