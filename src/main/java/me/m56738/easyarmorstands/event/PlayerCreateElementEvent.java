package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.element.ElementType;
import me.m56738.easyarmorstands.property.PropertyContainer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerCreateElementEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final ElementType type;
    private final PropertyContainer properties;
    private boolean cancelled;

    public PlayerCreateElementEvent(Player who, ElementType type, PropertyContainer properties) {
        super(who);
        this.type = type;
        this.properties = PropertyContainer.immutable(properties);
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public ElementType getType() {
        return type;
    }

    public PropertyContainer getProperties() {
        return properties;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
