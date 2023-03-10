package me.m56738.easyarmorstands.event;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player attempts to start editing an armor stand.
 */
public class SessionStartEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();

    private final ArmorStand armorStand;
    private boolean cancelled;

    public SessionStartEvent(@NotNull Player player, @NotNull ArmorStand armorStand) {
        super(player);
        this.armorStand = armorStand;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
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

    public @NotNull ArmorStand getArmorStand() {
        return armorStand;
    }
}
