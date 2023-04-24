package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.menu.ArmorStandMenu;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class SessionMenuInitializeEvent extends PlayerEvent {
    private static final HandlerList handlerList = new HandlerList();

    private final ArmorStandMenu menu;

    public SessionMenuInitializeEvent(@NotNull ArmorStandMenu menu) {
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

    public @NotNull ArmorStandMenu getMenu() {
        return menu;
    }
}
