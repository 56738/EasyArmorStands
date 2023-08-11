package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.editor.SimpleEntityObject;
import org.bukkit.event.Event;

public abstract class EntityObjectEvent extends Event {
    private final SimpleEntityObject entityObject;

    public EntityObjectEvent(SimpleEntityObject entityObject) {
        this.entityObject = entityObject;
    }

    public final SimpleEntityObject getEntityObject() {
        return entityObject;
    }
}
