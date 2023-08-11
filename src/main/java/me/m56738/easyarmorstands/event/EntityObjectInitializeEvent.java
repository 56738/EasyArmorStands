package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.editor.SimpleEntityObject;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityObjectInitializeEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final SimpleEntityObject entityObject;

    public EntityObjectInitializeEvent(SimpleEntityObject entityObject) {
        this.entityObject = entityObject;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public SimpleEntityObject getEntityObject() {
        return entityObject;
    }
}
