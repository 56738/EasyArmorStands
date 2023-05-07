package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.property.EntityProperty;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SessionEditEntityEvent<E extends Entity, T> extends SessionEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final E entity;
    private final EntityProperty<E, T> property;
    private final T oldValue;
    private final T newValue;
    private boolean cancelled;

    public SessionEditEntityEvent(Session session, E entity, EntityProperty<E, T> property, T oldValue, T newValue) {
        super(session);
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
