package me.m56738.easyarmorstands.session.v1_19_4;

import me.m56738.easyarmorstands.session.EntitySpawner;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;

import java.util.Objects;

public class DisplaySpawner<T extends Display> implements EntitySpawner<T> {
    private final Class<T> type;
    private final EntityType entityType;

    public DisplaySpawner(Class<T> type, EntityType entityType) {
        this.type = type;
        this.entityType = entityType;
    }

    @Override
    public EntityType getEntityType() {
        return entityType;
    }

    @Override
    public T spawn(Location location) {
        return Objects.requireNonNull(location.getWorld()).spawn(location, type);
    }
}
