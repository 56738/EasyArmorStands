package me.m56738.easyarmorstands.neoforge.api.event.session;

import me.m56738.easyarmorstands.api.editor.Session;
import net.neoforged.bus.api.Event;

public class SessionStopEvent extends Event {
    private final Session session;

    public SessionStopEvent(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
