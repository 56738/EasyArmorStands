package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.node.Node;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public interface EntitySpawner<T extends Entity> {
    T spawn(Location location);

    Node createNode(T entity);
}
