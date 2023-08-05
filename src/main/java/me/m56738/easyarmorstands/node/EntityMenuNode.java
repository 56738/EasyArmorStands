package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;

public class EntityMenuNode extends MenuNode implements EntityNode {
    private final Entity entity;

    public EntityMenuNode(Session session, Component name, Entity entity) {
        super(session, name);
        this.entity = entity;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && entity.isValid();
    }

    @Override
    public Entity getEntity() {
        return entity;
    }
}
