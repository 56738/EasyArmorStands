package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.element.Element;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when an element is selected.
 * <p>
 * Can be used to prevent selecting certain elements.
 */
public class SessionSelectElementEvent extends SessionEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final Element element;
    private boolean cancelled;

    public SessionSelectElementEvent(Session session, Element element) {
        super(session);
        this.element = element;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public @NotNull Element getElement() {
        return element;
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
