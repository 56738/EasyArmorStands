package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.property.PropertyChange;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerChangePropertyEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();

    private final PropertyChange<?> change;
    private boolean cancelled;

    public PlayerChangePropertyEvent(Player who, PropertyChange<?> change) {
        super(who);
        this.change = change;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public PropertyChange<?> getChange() {
        return change;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
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
