package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when an entity is selected.
 * <p>
 * Can be used to prevent selecting certain entities.
 */
public class SessionSelectEntityEvent extends SessionEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Entity entity;
    private boolean cancelled;

    public SessionSelectEntityEvent(Session session, Entity entity) {
        super(session);
        this.entity = entity;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public @NotNull Entity getEntity() {
        return entity;
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
