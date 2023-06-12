package me.m56738.easyarmorstands.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called after an entity is spawned.
 * <p>
 * Cannot be cancelled. Use {@link PlayerPreSpawnEntityEvent} to prevent spawning an entity.
 */
public class PlayerSpawnEntityEvent extends PlayerEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final Entity entity;

    public PlayerSpawnEntityEvent(Player player, Entity entity) {
        super(player);
        this.entity = entity;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public @NotNull Entity getEntity() {
        return entity;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
