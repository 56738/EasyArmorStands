package me.m56738.easyarmorstands.api.element;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface EntityElementProvider {
    @Nullable Element getElement(Entity entity);

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
