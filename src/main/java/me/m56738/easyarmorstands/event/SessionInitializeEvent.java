package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.session.Session;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a session is created.
 * <p>
 * Can be used to modify the created session, for example to add bones or tools.
 *
 * @see SessionStartEvent Preventing session creation
 */
public class SessionInitializeEvent extends SessionEvent {
    private static final HandlerList handlerList = new HandlerList();

    public SessionInitializeEvent(@NotNull Session session) {
        super(session);
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
