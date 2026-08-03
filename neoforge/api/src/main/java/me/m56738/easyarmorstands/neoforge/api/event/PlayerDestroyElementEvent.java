package me.m56738.easyarmorstands.neoforge.api.event;

import me.m56738.easyarmorstands.api.element.DestroyableElement;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerDestroyElementEvent extends PlayerEvent implements ICancellableEvent {
    private final DestroyableElement element;

    public PlayerDestroyElementEvent(Player player, DestroyableElement element) {
        super(player);
        this.element = element;
    }

    public DestroyableElement getElement() {
        return element;
    }
}
