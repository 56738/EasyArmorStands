package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.session.Session;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public abstract class SessionEvent extends PlayerEvent {
    private final Session session;

    public SessionEvent(Session session) {
        super(session.getPlayer());
        this.session = session;
    }

    public @NotNull Session getSession() {
        return session;
    }
}
