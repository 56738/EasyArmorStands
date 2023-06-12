package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called after an entity is spawned.
 * <p>
 * Cannot be cancelled. Use {@link SessionPreSpawnEvent} to prevent spawning an entity.
 */
public class SessionSpawnEvent extends SessionEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final Entity entity;

    public SessionSpawnEvent(Session session, Entity entity) {
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
}
