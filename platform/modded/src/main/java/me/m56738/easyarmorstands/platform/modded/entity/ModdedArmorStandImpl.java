package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.entity.decoration.ArmorStand;

record ModdedArmorStandImpl(ModdedPlatform platform, ArmorStand entity) implements ModdedArmorStand {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public ArmorStand getNative() {
        return entity;
    }
}
