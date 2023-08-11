package me.m56738.easyarmorstands.editor;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class EntityObjectReference implements EditableObjectReference {
    private final UUID uuid;

    public EntityObjectReference(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public @Nullable EditableObject restoreObject() {
        return null;
    }
}
