package me.m56738.easyarmorstands.bukkit.event;

import me.m56738.easyarmorstands.core.session.Session;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a session is created.
 * <p>
 * Can be used to modify the created session, for example to add bones or tools.
 *
 * @see SessionStartEvent Preventing session creation
 */
public class SessionInitializeEvent extends PlayerEvent {
    private static final HandlerList handlerList = new HandlerList();

    private final Session session;

    public SessionInitializeEvent(@NotNull Player player, @NotNull Session session) {
        super(player);
        this.session = session;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public @NotNull Session getSession() {
        return session;
    }
}
