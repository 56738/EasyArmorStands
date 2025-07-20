package me.m56738.easyarmorstands.paper.api.event.player;

import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.element.Element;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when an element is selected by a player using the editor or internally using a command.
 * <p>
 * Can be used to prevent selecting certain elements.
 */
public class PlayerSelectElementEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final EditableElement element;
    private boolean cancelled;

    public PlayerSelectElementEvent(@NotNull Player player, @NotNull EditableElement element) {
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
