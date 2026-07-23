package me.m56738.easyarmorstands.platform.paper.entity;

import me.m56738.easyarmorstands.platform.entity.Mob;

public interface PaperMob extends Mob, PaperLivingEntity {
    static PaperMob fromNative(org.bukkit.entity.Mob entity) {
        return new PaperMobImpl(entity);
    }

    org.bukkit.entity.Mob getNative();

    static org.bukkit.entity.Mob toNative(Mob entity) {
        return ((PaperMob) entity).getNative();
    }

    @Override
    default boolean hasAI() {
        return getNative().hasAI();
    }

    @Override
    default void setAI(boolean ai) {
        getNative().setAI(ai);
    }
}
