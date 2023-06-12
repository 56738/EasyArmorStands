package me.m56738.easyarmorstands.event;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called before spawning an entity, to test whether an entity may be spawned at that location.
 */
public class PlayerPreSpawnEntityEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Location location;
    private final EntityType type;
    private boolean cancelled;

    public PlayerPreSpawnEntityEvent(Player player, Location location, EntityType type) {
        super(player);
        this.location = location;
        this.type = type;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public @NotNull Location getLocation() {
        return location;
    }

    public @NotNull EntityType getType() {
        return type;
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
