package me.m56738.easyarmorstands.capability.armswing;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ArmSwingEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlerList = new HandlerList();
    private final EquipmentSlot hand;
    private boolean cancelled;

    public ArmSwingEvent(Player who, EquipmentSlot hand, boolean cancel) {
        super(who);
        this.hand = hand;
        cancelled = cancel;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public EquipmentSlot getHand() {
        return hand;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
