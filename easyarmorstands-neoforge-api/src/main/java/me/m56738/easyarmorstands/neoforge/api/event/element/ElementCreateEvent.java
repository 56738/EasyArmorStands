package me.m56738.easyarmorstands.neoforge.api.event.element;

import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

public class ElementCreateEvent extends Event implements ICancellableEvent {
    private final Player player;
    private final ElementType type;
    private final PropertyContainer properties;

    public ElementCreateEvent(Player player, ElementType type, PropertyContainer properties) {
        this.player = player;
        this.type = type;
        this.properties = properties;
    }

    public Player getPlayer() {
        return player;
    }

    public ElementType getType() {
        return type;
    }

    public PropertyContainer getProperties() {
        return properties;
    }
}
