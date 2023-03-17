package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.node.ClickableNode;
import org.bukkit.entity.Entity;

@FunctionalInterface
public interface EntityNodeProvider {
    ClickableNode createNode(Session session, Entity entity);
}
