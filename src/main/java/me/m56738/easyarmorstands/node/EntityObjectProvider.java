package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.editor.EntityObject;
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
