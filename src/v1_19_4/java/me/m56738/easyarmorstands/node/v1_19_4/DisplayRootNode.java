package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.node.ClickContext;
import me.m56738.easyarmorstands.node.ClickType;
import me.m56738.easyarmorstands.node.ElementNode;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.joml.Vector3dc;

public class DisplayRootNode extends DisplayMenuNode implements ElementNode {
    private final Session session;
    private final DisplayElement<?> element;

    public DisplayRootNode(Session session, Component name, DisplayElement<?> element) {
        super(session, name, PropertyContainer.tracked(element, session.getPlayer()));
        this.session = session;
        this.element = element;
    }

    @Override
    public boolean onClick(Vector3dc eyes, Vector3dc target, ClickContext context) {
        Player player = session.getPlayer();
        if (context.getType() == ClickType.LEFT_CLICK && player.hasPermission("easyarmorstands.open")) {
            element.openMenu(player);
            return true;
        }
        return super.onClick(eyes, target, context);
    }

    @Override
    public DisplayElement<?> getElement() {
        return element;
    }
}
