package me.m56738.easyarmorstands.api.event.element;

import me.m56738.easyarmorstands.api.element.ConfigurableEntityElement;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class EntityElementInitializeEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final ConfigurableEntityElement<?> element;

    public EntityElementInitializeEvent(@NotNull ConfigurableEntityElement<?> element) {
        this.element = element;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public @NotNull ConfigurableEntityElement<?> getElement() {
        return element;
    }
}
