package me.m56738.easyarmorstands.paper.api.event.session;

import me.m56738.easyarmorstands.api.editor.Session;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a session is started.
 */
public class SessionStartEvent extends SessionEvent {
    private static final HandlerList handlerList = new HandlerList();

    public SessionStartEvent(@NotNull Session session) {
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
