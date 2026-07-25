package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.entity.Interaction;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;

public interface ModdedInteraction extends Interaction, ModdedEntity {
    @Override
    net.minecraft.world.entity.Interaction getNative();

    static ModdedInteraction fromNative(ModdedPlatform platform, net.minecraft.world.entity.Interaction entity) {
        return new ModdedInteractionImpl(platform, entity);
    }

    static net.minecraft.world.entity.Interaction toNative(Interaction entity) {
        return ((ModdedInteraction) entity).getNative();
    }

    @Override
    default float getInteractionWidth() {
        return getNative().getWidth();
    }

    @Override
    default void setInteractionWidth(float width) {
        getNative().setWidth(width);
    }

    @Override
    default float getInteractionHeight() {
        return getNative().getHeight();
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    default void setInteractionHeight(float height) {
        getNative().setHeight(height);
    }

    @Override
    default boolean isResponsive() {
        return getNative().getResponse();
    }

    @Override
    default void setResponsive(boolean responsive) {
        getNative().setResponse(responsive);
    }
}
