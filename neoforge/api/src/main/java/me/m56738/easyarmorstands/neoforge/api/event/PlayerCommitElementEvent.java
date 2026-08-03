package me.m56738.easyarmorstands.neoforge.api.event;

import me.m56738.easyarmorstands.api.element.Element;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class PlayerCommitElementEvent extends PlayerEvent {
    private final Element element;

    public PlayerCommitElementEvent(Player player, Element element) {
        super(player);
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
