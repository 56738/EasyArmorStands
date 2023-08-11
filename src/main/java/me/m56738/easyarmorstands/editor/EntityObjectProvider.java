package me.m56738.easyarmorstands.editor;

import org.bukkit.entity.Entity;

public interface EntityObjectProvider {
    EntityObject createObject(Entity entity);

    default Priority getPriority() {
        return Priority.NORMAL;
    }

    enum Priority {
        HIGHEST,
        HIGH,
        NORMAL,
        LOW,
        LOWEST
    }
}
