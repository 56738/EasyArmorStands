package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.node.Node;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public interface EntitySpawner<T extends Entity> {
    EntityType getEntityType();

    T spawn(Location location);

    Node createNode(T entity);
}
