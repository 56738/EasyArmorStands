package gg.bundlegroup.easyarmorstands.api.event;

import gg.bundlegroup.easyarmorstands.api.Session;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerStartArmorStandEditorEvent extends ArmorStandSessionEvent implements Cancellable {
    private boolean cancelled = false;

    public PlayerStartArmorStandEditorEvent(Session session) {
        super(session);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
