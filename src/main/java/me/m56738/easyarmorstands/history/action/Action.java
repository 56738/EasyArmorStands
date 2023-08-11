package me.m56738.easyarmorstands.history.action;

import net.kyori.adventure.text.Component;

import java.util.UUID;

public interface Action {
    boolean execute();

    boolean undo();

    Component describe();

    /**
     * When an entity is spawned, it has a random UUID.
     * For example, to undo a destroy action, we spawn a replacement entity.
     * All references to the old UUID must be updated to the new UUID.
     *
     * @param oldId The UUID of the entity which was replaced.
     * @param newId The UUID of the replacement.
     */
    @Deprecated
    void onEntityReplaced(UUID oldId, UUID newId);
}
