package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.entity.decoration.Mannequin;

record ModdedMannequinImpl(ModdedPlatform platform, Mannequin entity) implements ModdedMannequin {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Mannequin getNative() {
        return entity;
    }
}
