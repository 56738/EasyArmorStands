package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.nbt.CompoundTag;

record ModdedEntitySnapshotImpl(ModdedPlatform platform, CompoundTag tag) implements ModdedEntitySnapshot {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public CompoundTag getNative() {
        return tag;
    }
}
