package me.m56738.easyarmorstands.api.event.element;

import me.m56738.easyarmorstands.api.element.ConfigurableEntityElement;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityElementInitializeEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final ConfigurableEntityElement<?> element;

    public EntityElementInitializeEvent(ConfigurableEntityElement<?> element) {
        this.element = element;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public ConfigurableEntityElement<?> getElement() {
        return element;
    }
}
