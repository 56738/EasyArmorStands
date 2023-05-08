package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.menu.EntityMenu;
import me.m56738.easyarmorstands.node.ClickContext;
import me.m56738.easyarmorstands.node.ClickType;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.joml.Vector3dc;

public class DisplayRootNode extends DisplayMenuNode {
    private final Session session;
    private final Display entity;

    public DisplayRootNode(Session session, Component name, Display entity) {
        super(session, name, entity);
        this.session = session;
        this.entity = entity;
    }

    @Override
    public boolean onClick(Vector3dc eyes, Vector3dc target, ClickContext context) {
        if (context.getType() == ClickType.LEFT_CLICK) {
            session.getPlayer().openInventory(new EntityMenu<>(session, entity).getInventory());
            return true;
        }
        return super.onClick(eyes, target, context);
    }
}
