package me.m56738.easyarmorstands.paper.api.event.element;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.element.DefaultEntityElement;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when an entity element is being initialized.
 * <p>
 * Allows {@link me.m56738.easyarmorstands.api.property.PropertyRegistry#register(Property) registering} custom
 * properties into the {@link DefaultEntityElement#getProperties() property container} of the
 * {@link #getElement() element}.
 */
public class EntityElementInitializeEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final DefaultEntityElement element;

    public EntityElementInitializeEvent(@NotNull DefaultEntityElement element) {
        this.element = element;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public @NotNull DefaultEntityElement getElement() {
        return element;
    }
}
