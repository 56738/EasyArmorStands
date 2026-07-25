package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.entity.Display;

record ModdedItemDisplayImpl(ModdedPlatform platform, Display.ItemDisplay entity) implements ModdedItemDisplay {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Display.ItemDisplay getNative() {
        return entity;
    }
}
