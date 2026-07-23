package me.m56738.easyarmorstands.platform.paper.entity;

import me.m56738.easyarmorstands.platform.entity.EntityType;
import net.kyori.adventure.key.Key;

public interface PaperEntityType extends EntityType {
    static PaperEntityType fromNative(org.bukkit.entity.EntityType type) {
        return new PaperEntityTypeImpl(type);
    }

    org.bukkit.entity.EntityType getNative();

    static org.bukkit.entity.EntityType toNative(EntityType type) {
        return ((PaperEntityType) type).getNative();
    }

    @Override
    default Key key() {
        return getNative().key();
    }

    @Override
    default String translationKey() {
        return getNative().translationKey();
    }
}
