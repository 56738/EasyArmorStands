package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.node.Node;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Nullable;

public interface EntitySpawner<T extends Entity> {
    EntityType getEntityType();

    T spawn(Location location);

    @Nullable Node createNode(T entity);
}
