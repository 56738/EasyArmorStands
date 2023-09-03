package me.m56738.easyarmorstands.api.history;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface EntityReplacementListener {
    /**
     * When an entity is spawned, it has a random UUID.
     * For example, to undo a destroy action, we spawn a replacement entity.
     * All references to the old UUID must be updated to the new UUID.
     *
     * @param oldId The UUID of the entity which was replaced.
     * @param newId The UUID of the replacement.
     */
    void onEntityReplaced(@NotNull UUID oldId, @NotNull UUID newId);
}
