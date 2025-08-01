package me.m56738.easyarmorstands.neoforge.api.event.element;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.Property;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

public class ElementPropertyChangeEvent extends Event implements ICancellableEvent {
    private final Player player;
    private final Element element;
    private final PropertyChange<?> change;

    public ElementPropertyChangeEvent(Player player, Element element, PropertyChange<?> change) {
        this.player = player;
        this.element = element;
        this.change = change;
    }

    public Player getPlayer() {
        return player;
    }

    public Element getElement() {
        return element;
    }

    public PropertyChange<?> getChange() {
        return change;
    }

    public record PropertyChange<T>(Property<T> property, T value) {
    }
}
