package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.menu.EntityMenu;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a session menu is populated.
 * <p>
 * Can be used to add buttons.
 */
public class SessionMenuInitializeEvent extends PlayerEvent {
    private static final HandlerList handlerList = new HandlerList();

    private final EntityMenu<?> menu;

    public SessionMenuInitializeEvent(@NotNull EntityMenu<?> menu) {
        super(menu.getSession().getPlayer());
        this.menu = menu;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public @NotNull EntityMenu<?> getMenu() {
        return menu;
    }
}
