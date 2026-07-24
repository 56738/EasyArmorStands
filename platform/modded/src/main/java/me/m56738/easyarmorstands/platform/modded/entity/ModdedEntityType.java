package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.entity.EntityType;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.modcommon.MinecraftAudiences;
import net.minecraft.core.registries.BuiltInRegistries;

public interface ModdedEntityType extends EntityType, ModdedPlatformHolder {
    net.minecraft.world.entity.EntityType<?> getNative();

    static ModdedEntityType fromNative(ModdedPlatform platform, net.minecraft.world.entity.EntityType<?> type) {
        return new ModdedEntityTypeImpl(platform, type);
    }

    static net.minecraft.world.entity.EntityType<?> toNative(EntityType type) {
        return ((ModdedEntityType) type).getNative();
    }

    @Override
    default Key key() {
        return MinecraftAudiences.asAdventure(BuiltInRegistries.ENTITY_TYPE.getKey(getNative()));
    }

    @Override
    default String translationKey() {
        return getNative().getDescriptionId();
    }
}
