package me.m56738.easyarmorstands.neoforge.api.event;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.Property;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerEditPropertyEvent<T> extends PlayerEvent implements ICancellableEvent {
    private final Element element;
    private final Property<T> property;
    private final T oldValue;
    private final T newValue;

    public PlayerEditPropertyEvent(Player player, Element element, Property<T> property, T oldValue, T newValue) {
        super(player);
        this.element = element;
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Element getElement() {
        return element;
    }

    public Property<T> getProperty() {
        return property;
    }

    public T getOldValue() {
        return oldValue;
    }

    public T getNewValue() {
        return newValue;
    }
}
