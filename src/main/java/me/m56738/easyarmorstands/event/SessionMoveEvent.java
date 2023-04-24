package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.session.Session;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class SessionMoveEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();

    private final Session session;
    private final Entity entity;
    private final Location location;
    private boolean cancelled;

    public SessionMoveEvent(Session session, Entity entity, Location location) {
        super(session.getPlayer());
        this.session = session;
        this.entity = entity;
        this.location = location;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public @NotNull Session getSession() {
        return session;
    }

    public @NotNull Entity getEntity() {
        return entity;
    }

    public @NotNull Location getLocation() {
        return location.clone();
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
