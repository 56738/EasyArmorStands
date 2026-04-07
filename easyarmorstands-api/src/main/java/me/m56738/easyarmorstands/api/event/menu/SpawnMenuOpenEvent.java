package me.m56738.easyarmorstands.api.event.menu;

import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class SpawnMenuOpenEvent extends PlayerEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final MenuBuilder builder;

    public SpawnMenuOpenEvent(Player player, MenuBuilder builder) {
        super(player);
        this.builder = builder;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public MenuBuilder getBuilder() {
        return builder;
    }
}
