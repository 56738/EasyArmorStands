package me.m56738.easyarmorstands.editor;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EntityObjectReference implements EditableObjectReference {
    private UUID uuid;

    public EntityObjectReference(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public @Nullable EditableObject restoreObject() {
        return null;
    }

    @Override
    public void onEntityReplaced(UUID oldId, UUID newId) {
        if (uuid.equals(oldId)) {
            uuid = newId;
        }
    }
}
