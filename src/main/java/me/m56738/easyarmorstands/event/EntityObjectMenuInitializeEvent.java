package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.editor.SimpleEntityObject;
import me.m56738.easyarmorstands.menu.builder.MenuBuilder;
import org.bukkit.event.HandlerList;

public class EntityObjectMenuInitializeEvent extends EntityObjectEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final MenuBuilder menuBuilder;

    public EntityObjectMenuInitializeEvent(SimpleEntityObject entityObject, MenuBuilder menuBuilder) {
        super(entityObject);
        this.menuBuilder = menuBuilder;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public MenuBuilder getMenuBuilder() {
        return menuBuilder;
    }
}
