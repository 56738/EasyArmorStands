package me.m56738.easyarmorstands.paper.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import net.kyori.adventure.text.Component;

public interface PaperEntityType extends EntityType {
    static PaperEntityType fromNative(org.bukkit.entity.EntityType nativeType) {
        return new PaperEntityTypeImpl(nativeType);
    }

    static org.bukkit.entity.EntityType toNative(EntityType type) {
        return ((PaperEntityType) type).getNative();
    }

    org.bukkit.entity.EntityType getNative();

    @Override
    default String name() {
        return getNative().name();
    }

    @Override
    default Component asComponent() {
        return Component.translatable(getNative().translationKey());
    }
}
