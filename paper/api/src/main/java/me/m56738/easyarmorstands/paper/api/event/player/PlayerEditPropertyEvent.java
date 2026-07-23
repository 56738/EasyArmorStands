package me.m56738.easyarmorstands.paper.api.event.player;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.Property;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Called when an element property is modified.
 */
public class PlayerEditPropertyEvent<T> extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Element element;
    private final Property<T> property;
    private final T oldValue;
    private final T newValue;
    private boolean cancelled;

    public PlayerEditPropertyEvent(@NotNull Player player, @NotNull Element element, @NotNull Property<T> property, @NotNull T oldValue, @NotNull T newValue) {
        super(player);
        this.element = element;
        this.property = property;
        this.oldValue = Objects.requireNonNull(oldValue);
        this.newValue = Objects.requireNonNull(newValue);
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public @NotNull Element getElement() {
        return element;
    }

    public @NotNull Property<T> getProperty() {
        return property;
    }

    public @NotNull T getOldValue() {
        return oldValue;
    }

    public @NotNull T getNewValue() {
        return newValue;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
