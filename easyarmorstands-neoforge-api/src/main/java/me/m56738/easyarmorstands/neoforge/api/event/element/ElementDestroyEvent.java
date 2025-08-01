package me.m56738.easyarmorstands.neoforge.api.event.element;

import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

public class ElementDestroyEvent extends Event implements ICancellableEvent {
    private final Player player;
    private final DestroyableElement element;

    public ElementDestroyEvent(Player player, DestroyableElement element) {
        this.player = player;
        this.element = element;
    }

    public Player getPlayer() {
        return player;
    }

    public DestroyableElement getElement() {
        return element;
    }
}
