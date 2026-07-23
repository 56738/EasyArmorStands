package me.m56738.easyarmorstands.platform.paper.entity;

import me.m56738.easyarmorstands.platform.entity.Interaction;

public interface PaperInteraction extends Interaction, PaperEntity {
    static PaperInteraction fromNative(org.bukkit.entity.Interaction entity) {
        return new PaperInteractionImpl(entity);
    }

    org.bukkit.entity.Interaction getNative();

    static org.bukkit.entity.Interaction toNative(Interaction entity) {
        return ((PaperInteraction) entity).getNative();
    }

    @Override
    default float getInteractionWidth() {
        return getNative().getInteractionWidth();
    }

    @Override
    default void setInteractionWidth(float width) {
        getNative().setInteractionWidth(width);
    }

    @Override
    default float getInteractionHeight() {
        return getNative().getInteractionHeight();
    }

    @Override
    default void setInteractionHeight(float height) {
        getNative().setInteractionHeight(height);
    }

    @Override
    default boolean isResponsive() {
        return getNative().isResponsive();
    }

    @Override
    default void setResponsive(boolean responsive) {
        getNative().setResponsive(responsive);
    }
}
