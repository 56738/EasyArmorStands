package me.m56738.easyarmorstands.api.event.element;

import me.m56738.easyarmorstands.api.element.ConfigurableEntityElement;
import me.m56738.easyarmorstands.api.property.Property;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when an entity element is being initialized.
 * <p>
 * Allows {@link me.m56738.easyarmorstands.api.property.PropertyRegistry#register(Property) registering} custom
 * properties into the {@link ConfigurableEntityElement#getProperties() property container} of the
 * {@link #getElement() element}.
 */
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
