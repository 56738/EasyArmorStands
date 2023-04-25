package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.node.Button;
import org.bukkit.entity.Entity;

@FunctionalInterface
public interface EntityButtonProvider {
    Button createButton(Session session, Entity entity);
}
