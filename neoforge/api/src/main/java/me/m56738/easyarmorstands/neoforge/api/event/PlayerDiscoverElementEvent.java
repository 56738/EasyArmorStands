package me.m56738.easyarmorstands.neoforge.api.event;

import me.m56738.easyarmorstands.api.element.EditableElement;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerDiscoverElementEvent extends PlayerEvent implements ICancellableEvent {
    private final EditableElement element;

    public PlayerDiscoverElementEvent(Player player, EditableElement element) {
        super(player);
        this.element = element;
    }

    public EditableElement getElement() {
        return element;
    }
}
