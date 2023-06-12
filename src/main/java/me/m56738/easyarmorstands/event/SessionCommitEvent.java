package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.session.Session;
import org.bukkit.event.HandlerList;

/**
 * Called when committing changes performed by a session, before they are persisted as a history action.
 */
public class SessionCommitEvent extends SessionEvent {
    private static final HandlerList handlerList = new HandlerList();

    public SessionCommitEvent(Session session) {
        super(session);
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
