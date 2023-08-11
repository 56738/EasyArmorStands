package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.editor.SimpleEntityObject;
import me.m56738.easyarmorstands.menu.builder.MenuBuilder;
import me.m56738.easyarmorstands.property.PropertyContainer;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class EntityObjectMenuInitializeEvent extends EntityObjectEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final Player player;
    private final MenuBuilder menuBuilder;
    private final PropertyContainer properties;
    private Component title;

    public EntityObjectMenuInitializeEvent(Player player, SimpleEntityObject entityObject, MenuBuilder menuBuilder, Component title) {
        super(entityObject);
        this.player = player;
        this.menuBuilder = menuBuilder;
        this.properties = PropertyContainer.tracked(entityObject, player);
        this.title = title;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }

    public MenuBuilder getMenuBuilder() {
        return menuBuilder;
    }

    public PropertyContainer getProperties() {
        return properties;
    }

    public Component getTitle() {
        return title;
    }

    public void setTitle(Component title) {
        this.title = title;
    }
}
