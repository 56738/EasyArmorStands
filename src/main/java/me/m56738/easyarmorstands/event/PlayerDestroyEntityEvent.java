package me.m56738.easyarmorstands.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called when a player attempts to destroy an entity using EasyArmorStands.
 */
public class PlayerDestroyEntityEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Entity entity;
    private boolean cancelled;

    public PlayerDestroyEntityEvent(Player who, Entity entity) {
        super(who);
        this.entity = entity;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public Entity getEntity() {
        return entity;
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
