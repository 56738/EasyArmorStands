package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.entity.Display;

record ModdedTextDisplayImpl(ModdedPlatform platform, Display.TextDisplay entity) implements ModdedTextDisplay {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Display.TextDisplay getNative() {
        return entity;
    }
}
