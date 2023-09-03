package me.m56738.easyarmorstands.api.event.session;

import me.m56738.easyarmorstands.api.editor.Session;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public abstract class SessionEvent extends PlayerEvent {
    private final Session session;

    public SessionEvent(Session session) {
        super(session.player());
        this.session = session;
    }

    public @NotNull Session getSession() {
        return session;
    }
}
