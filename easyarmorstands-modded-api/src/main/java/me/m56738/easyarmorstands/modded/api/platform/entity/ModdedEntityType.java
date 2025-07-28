package me.m56738.easyarmorstands.modded.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import net.kyori.adventure.text.Component;

public interface ModdedEntityType extends EntityType {
    static ModdedEntityType fromNative(net.minecraft.world.entity.EntityType<?> nativeType) {
        return new ModdedEntityTypeImpl(nativeType);
    }

    static net.minecraft.world.entity.EntityType<?> toNative(EntityType type) {
        return ((ModdedEntityType) type).getNative();
    }

    net.minecraft.world.entity.EntityType<?> getNative();

    @Override
    default String name() {
        return getNative().toShortString();
    }

    @Override
    default Component asComponent() {
        return Component.translatable(getNative().getDescriptionId());
    }
}
