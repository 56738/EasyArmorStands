package me.m56738.easyarmorstands.api.event;

import me.m56738.easyarmorstands.api.Session;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ArmorStandSessionEvent extends PlayerEvent {
    private final Session session;

    public ArmorStandSessionEvent(Session session) {
        super(session.player());
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
