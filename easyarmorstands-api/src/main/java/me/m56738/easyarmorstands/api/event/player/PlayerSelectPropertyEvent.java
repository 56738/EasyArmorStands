package me.m56738.easyarmorstands.api.event.player;

import me.m56738.easyarmorstands.api.property.Property;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerSelectPropertyEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Property<?> property;
    private boolean cancelled;

    public PlayerSelectPropertyEvent(@NotNull Player player, @NotNull Property<?> property) {
        super(player);
        this.property = property;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public @NotNull Property<?> getProperty() {
        return property;
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
