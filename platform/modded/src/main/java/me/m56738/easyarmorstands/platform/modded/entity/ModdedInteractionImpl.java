package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.entity.Interaction;

record ModdedInteractionImpl(ModdedPlatform platform, Interaction entity) implements ModdedInteraction {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Interaction getNative() {
        return entity;
    }
}
