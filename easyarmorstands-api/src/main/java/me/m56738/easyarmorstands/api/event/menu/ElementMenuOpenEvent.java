package me.m56738.easyarmorstands.api.event.menu;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ElementMenuOpenEvent extends PlayerEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final Element element;
    private final MenuBuilder builder;
    private final PropertyContainer properties;

    public ElementMenuOpenEvent(Player player, Element element, MenuBuilder builder, PropertyContainer properties) {
        super(player);
        this.element = element;
        this.builder = builder;
        this.properties = properties;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Element getElement() {
        return element;
    }

    public MenuBuilder getBuilder() {
        return builder;
    }

    public PropertyContainer getProperties() {
        return properties;
    }
}
