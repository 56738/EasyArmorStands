package me.m56738.easyarmorstands.event;

import me.m56738.easyarmorstands.menu.builder.MenuBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpawnMenuInitializeEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final Player player;
    private final MenuBuilder menuBuilder;

    public SpawnMenuInitializeEvent(Player player, MenuBuilder menuBuilder) {
        this.player = player;
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

    public MenuBuilder getMenuBuilder() {
        return menuBuilder;
    }
}
