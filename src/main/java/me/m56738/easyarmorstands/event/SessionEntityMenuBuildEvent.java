package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.menu.builder.MenuBuilder;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;

public class SessionEntityMenuBuildEvent extends SessionMenuBuildEvent {
    private static final HandlerList handlerList = new HandlerList();

    private final Entity entity;

    public SessionEntityMenuBuildEvent(Session session, MenuBuilder builder, Entity entity) {
        super(session, builder);
        this.entity = entity;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Entity getEntity() {
        return entity;
    }
}
