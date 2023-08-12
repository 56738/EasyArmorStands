package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.element.Element;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class ElementDestroyAction extends ElementPresenceAction {
    public ElementDestroyAction(Element element) {
        super(element);
    }

    @Override
    public boolean execute(Player player) {
        return destroy(player);
    }

    @Override
    public boolean undo(Player player) {
        return create(player);
    }

    @Override
    public Component describe() {
        return Component.text("Deleted ").append(getType().getDisplayName());
    }
}
