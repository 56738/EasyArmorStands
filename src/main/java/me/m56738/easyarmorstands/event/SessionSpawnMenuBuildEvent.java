package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.menu.builder.MenuBuilder;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.event.HandlerList;

public class SessionSpawnMenuBuildEvent extends SessionMenuBuildEvent {
    private static final HandlerList handlerList = new HandlerList();

    public SessionSpawnMenuBuildEvent(Session session, MenuBuilder builder) {
        super(session, builder);
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
