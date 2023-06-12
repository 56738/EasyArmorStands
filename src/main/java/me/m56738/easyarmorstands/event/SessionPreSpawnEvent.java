package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.session.Session;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called before spawning an entity, to test whether an entity may be spawned at that location.
 */
public class SessionPreSpawnEvent extends SessionEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Location location;
    private final EntityType type;
    private boolean cancelled;

    public SessionPreSpawnEvent(Session session, Location location, EntityType type) {
        super(session);
        this.location = location;
        this.type = type;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public @NotNull Location getLocation() {
        return location;
    }

    public @NotNull EntityType getType() {
        return type;
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
