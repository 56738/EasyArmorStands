package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.menu.SpawnMenu;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when the spawn menu is populated.
 * <p>
 * Can be used to add custom spawn buttons.
 */
public class SessionSpawnMenuInitializeEvent extends SessionEvent {
    private static final HandlerList handlerList = new HandlerList();

    private final SpawnMenu menu;

    public SessionSpawnMenuInitializeEvent(@NotNull SpawnMenu menu) {
        super(menu.getSession());
        this.menu = menu;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public @NotNull SpawnMenu getMenu() {
        return menu;
    }
}
