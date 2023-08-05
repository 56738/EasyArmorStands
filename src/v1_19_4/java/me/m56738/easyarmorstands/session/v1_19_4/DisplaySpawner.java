package me.m56738.easyarmorstands.session.v1_19_4;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import me.m56738.easyarmorstands.session.EntitySpawner;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class DisplaySpawner<T extends Display> implements EntitySpawner<T> {
    private final Class<T> type;
    private final EntityType entityType;
    private final JOMLMapper mapper;

    public DisplaySpawner(Class<T> type, EntityType entityType, JOMLMapper mapper) {
        this.type = type;
        this.entityType = entityType;
        this.mapper = mapper;
    }

    @Override
    public EntityType getEntityType() {
        return entityType;
    }

    @Override
    public T spawn(Location location) {
        SpawnCapability spawnCapability = EasyArmorStands.getInstance().getCapability(SpawnCapability.class);
        float yaw = location.getYaw();
        location = location.clone();
        location.setYaw(0);
        location.setPitch(0);
        return spawnCapability.spawnEntity(location, type, e -> {
            e.setTransformation(mapper.getTransformation(
                    new Vector3f(),
                    new Quaternionf().rotationY((float) -Math.toRadians(yaw)),
                    new Vector3f(1),
                    new Quaternionf()));
            configure(e);
        });
    }

    protected void configure(T entity) {
    }
}
