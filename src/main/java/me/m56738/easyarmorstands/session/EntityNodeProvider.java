package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.node.EntityNode;
import org.bukkit.entity.Entity;

@FunctionalInterface
public interface EntityNodeProvider {
    EntityNode createNode(Session session, Entity entity);
}
