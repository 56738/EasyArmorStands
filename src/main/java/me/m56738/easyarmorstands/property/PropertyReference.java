package me.m56738.easyarmorstands.property;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface PropertyReference<T> {
    @Nullable Property<T> restore();

    /**
     * When an entity is spawned, it has a random UUID.
     * For example, to undo a destroy action, we spawn a replacement entity.
     * All references to the old UUID must be updated to the new UUID.
     *
     * @param oldId The UUID of the entity which was replaced.
     * @param newId The UUID of the replacement.
     * @return A property reference where all references to the UUID are replaced.
     */
    default PropertyReference<T> replaceEntity(UUID oldId, UUID newId) {
        return this;
    }
}
