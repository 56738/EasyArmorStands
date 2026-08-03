package me.m56738.easyarmorstands.neoforge.api.event;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerElementMenuOpenEvent extends PlayerEvent {
    private final Element element;
    private final MenuBuilder menuBuilder;
    private final PropertyContainer properties;

    public PlayerElementMenuOpenEvent(Player player, Element element, MenuBuilder menuBuilder, PropertyContainer properties) {
        super(player);
        this.element = element;
        this.menuBuilder = menuBuilder;
        this.properties = properties;
    }

    public Element getElement() {
        return element;
    }

    public MenuBuilder getMenuBuilder() {
        return menuBuilder;
    }

    public PropertyContainer getProperties() {
        return properties;
    }
}
