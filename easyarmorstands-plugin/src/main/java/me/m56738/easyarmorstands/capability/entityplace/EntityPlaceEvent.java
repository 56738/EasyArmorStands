package me.m56738.easyarmorstands.capability.entityplace;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

public class EntityPlaceEvent extends EntityEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final Player player;

    public EntityPlaceEvent(Entity what, Player who) {
        super(what);
        this.player = who;
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
}
