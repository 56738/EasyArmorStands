package me.m56738.easyarmorstands.neoforge.api.event.element;

import me.m56738.easyarmorstands.api.element.EditableElement;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

public class ElementSelectEvent extends Event implements ICancellableEvent {
    private final Player player;
    private final EditableElement element;

    public ElementSelectEvent(Player player, EditableElement element) {
        this.player = player;
        this.element = element;
    }

    public Player getPlayer() {
        return player;
    }

    public EditableElement getElement() {
        return element;
    }
}
