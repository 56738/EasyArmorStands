package me.m56738.easyarmorstands.api.event.menu;

import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Locale;

public class SpawnMenuInitializeEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Player player;
    private final Locale locale;
    private final MenuBuilder menuBuilder;

    public SpawnMenuInitializeEvent(Player player, Locale locale, MenuBuilder menuBuilder) {
        this.player = player;
        this.locale = locale;
        this.menuBuilder = menuBuilder;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }

    public Locale getLocale() {
        return locale;
    }

    public MenuBuilder getMenuBuilder() {
        return menuBuilder;
    }
}
