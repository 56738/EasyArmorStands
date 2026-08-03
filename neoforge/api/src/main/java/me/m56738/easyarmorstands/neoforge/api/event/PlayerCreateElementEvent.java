package me.m56738.easyarmorstands.neoforge.api.event;

import me.m56738.easyarmorstands.api.element.ElementType;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerCreateElementEvent extends PlayerEvent implements ICancellableEvent {
    private final ElementType type;
    private final PropertyContainer properties;

    public PlayerCreateElementEvent(Player player, ElementType type, PropertyContainer properties) {
        super(player);
        this.type = type;
        this.properties = properties;
    }

    public ElementType getType() {
        return type;
    }

    public PropertyContainer getProperties() {
        return properties;
    }
}
