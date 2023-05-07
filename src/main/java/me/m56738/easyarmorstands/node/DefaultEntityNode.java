package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.bone.EntityLocationBone;
import me.m56738.easyarmorstands.bone.PositionBone;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;

public class DefaultEntityNode extends MenuNode implements EntityNode {
    private final Entity entity;

    public DefaultEntityNode(Session session, Entity entity) {
        super(session, Component.text("Position"));
        this.entity = entity;
        PositionBone positionBone = new EntityLocationBone(session, entity);
        addPositionButtons(session, positionBone, 3, true);
        setRoot(true);
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
