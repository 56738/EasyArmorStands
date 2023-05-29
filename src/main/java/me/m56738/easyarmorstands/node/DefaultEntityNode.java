package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.EntityLocationBone;
import me.m56738.easyarmorstands.menu.EntityMenu;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.joml.Vector3dc;

public class DefaultEntityNode extends MenuNode implements EntityNode {
    private final Session session;
    private final Entity entity;

    public DefaultEntityNode(Session session, Entity entity) {
        super(session, Component.text("Position"));
        this.session = session;
        this.entity = entity;
        EntityLocationBone positionBone = new EntityLocationBone(session, entity);
        addPositionButtons(session, positionBone, 3, true);
        addCarryButtonWithYaw(session, positionBone);
        setRoot(true);
    }

    @Override
    public boolean onClick(Vector3dc eyes, Vector3dc target, ClickContext context) {
        if (context.getType() == ClickType.LEFT_CLICK) {
            session.getPlayer().openInventory(new EntityMenu<>(session, entity).getInventory());
            return true;
        }
        return super.onClick(eyes, target, context);
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && entity.isValid();
    }
}
