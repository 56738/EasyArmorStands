package me.m56738.easyarmorstands.bukkit.event;

import me.m56738.easyarmorstands.bukkit.platform.BukkitPlayer;
import me.m56738.easyarmorstands.core.inventory.SessionMenu;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class SessionMenuInitializeEvent extends PlayerEvent {
    private static final HandlerList handlerList = new HandlerList();

    private final SessionMenu menu;

    public SessionMenuInitializeEvent(@NotNull SessionMenu menu) {
        super(((BukkitPlayer) menu.getSession().getPlayer()).get());
        this.menu = menu;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    public @NotNull SessionMenu getMenu() {
        return menu;
    }
}
