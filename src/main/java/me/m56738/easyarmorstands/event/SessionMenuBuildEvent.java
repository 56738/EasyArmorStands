package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.menu.builder.MenuBuilder;
import me.m56738.easyarmorstands.session.Session;

public abstract class SessionMenuBuildEvent extends SessionEvent {
    private final MenuBuilder builder;

    public SessionMenuBuildEvent(Session session, MenuBuilder builder) {
        super(session);
        this.builder = builder;
    }

    public MenuBuilder getBuilder() {
        return builder;
    }
}
