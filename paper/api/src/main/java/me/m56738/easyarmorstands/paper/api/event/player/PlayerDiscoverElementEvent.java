package me.m56738.easyarmorstands.paper.api.event.player;

import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called to check whether an element can be discovered by a player.
 * <p>
 * Can be used to prevent displaying buttons for certain elements.
 */
public class PlayerDiscoverElementEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final EditableElement element;
    private boolean cancelled;

    public PlayerDiscoverElementEvent(@NotNull Player player, @NotNull EditableElement element) {
        super(player);
        this.element = element;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public @NotNull Element getElement() {
        return element;
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
