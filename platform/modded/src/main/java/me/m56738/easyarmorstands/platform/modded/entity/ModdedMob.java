package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.entity.Mob;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;

public interface ModdedMob extends Mob, ModdedLivingEntity {
    @Override
    net.minecraft.world.entity.Mob getNative();

    static ModdedMob fromNative(ModdedPlatform platform, net.minecraft.world.entity.Mob entity) {
        return new ModdedMobImpl(platform, entity);
    }

    static net.minecraft.world.entity.Mob toNative(Mob entity) {
        return ((ModdedMob) entity).getNative();
    }

    @Override
    default boolean hasAI() {
        return !getNative().isNoAi();
    }

    @Override
    default void setAI(boolean ai) {
        getNative().setNoAi(!ai);
    }
}
