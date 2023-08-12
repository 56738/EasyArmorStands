package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.element.MenuElement;
import me.m56738.easyarmorstands.menu.builder.MenuBuilder;
import me.m56738.easyarmorstands.property.PropertyContainer;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityElementMenuInitializeEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Player player;
    private final MenuElement element;
    private final MenuBuilder menuBuilder;
    private final PropertyContainer properties;
    private Component title;

    public EntityElementMenuInitializeEvent(Player player, MenuElement element, MenuBuilder menuBuilder, PropertyContainer properties, Component title) {
        this.player = player;
        this.element = element;
        this.menuBuilder = menuBuilder;
        this.properties = properties;
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

    public MenuElement getElement() {
        return element;
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
