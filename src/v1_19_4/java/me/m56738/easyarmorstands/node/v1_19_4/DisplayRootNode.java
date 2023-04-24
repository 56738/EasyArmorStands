package me.m56738.easyarmorstands.node.v1_19_4;

import me.m56738.easyarmorstands.menu.v1_19_4.ItemDisplayMenu;
import me.m56738.easyarmorstands.node.ClickContext;
import me.m56738.easyarmorstands.node.ClickType;
import me.m56738.easyarmorstands.node.ParentNode;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.joml.Vector3dc;

public class DisplayRootNode extends ParentNode {
    private final Session session;
    private final Display entity;

    public DisplayRootNode(Session session, Component name, Display entity) {
        super(session, name);
        this.session = session;
        this.entity = entity;
    }

    @Override
    public boolean onClick(Vector3dc eyes, Vector3dc target, ClickContext context) {
        if (context.getType() == ClickType.LEFT_CLICK) {
            if (entity instanceof ItemDisplay) {
                session.getPlayer().openInventory(new ItemDisplayMenu(session, (ItemDisplay) entity).getInventory());
            }
            return true;
        }

        return super.onClick(eyes, target, context);
    }
}
