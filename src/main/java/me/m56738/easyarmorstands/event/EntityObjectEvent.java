package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.editor.EntityObject;
import me.m56738.easyarmorstands.editor.SimpleEntityObject;
import org.bukkit.event.Event;

public abstract class EntityObjectEvent extends Event {
    private final EntityObject entityObject;

    public EntityObjectEvent(SimpleEntityObject entityObject) {
        this.entityObject = entityObject;
    }

    public final EntityObject getEntityObject() {
        return entityObject;
    }
}
