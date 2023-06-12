package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.property.EntityProperty;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when an entity property is modified.
 */
public class PlayerEditEntityPropertyEvent<E extends Entity, T> extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final E entity;
    private final EntityProperty<E, T> property;
    private final T oldValue;
    private final T newValue;
    private boolean cancelled;

    public PlayerEditEntityPropertyEvent(Player player, E entity, EntityProperty<E, T> property, T oldValue, T newValue) {
        super(player);
        this.entity = entity;
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public @NotNull E getEntity() {
        return entity;
    }

    public @NotNull EntityProperty<E, T> getProperty() {
        return property;
    }

    public @NotNull T getOldValue() {
        return oldValue;
    }

    public @NotNull T getNewValue() {
        return newValue;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
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
