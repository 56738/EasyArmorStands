package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasEntity;
import gg.bundlegroup.easyarmorstands.platform.EasWorld;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityGlowSetter;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityPersistenceSetter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BukkitEntity<T extends Entity> extends BukkitWrapper<T> implements EasEntity {
    private final EntityPersistenceSetter entityPersistenceSetter;
    private final EntityGlowSetter entityGlowSetter;
    private final Vector3d position = new Vector3d();

    public BukkitEntity(BukkitPlatform platform, T entity) {
        super(platform, entity);
        this.entityPersistenceSetter = platform.entityPersistenceSetter();
        this.entityGlowSetter = platform.entityGlowSetter();
    }

    @Override
    public void update() {
        Location location = get().getLocation();
        position.set(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public void teleport(Vector3dc position, float yaw, float pitch) {
        get().teleport(new Location(get().getWorld(), position.x(), position.y(), position.z(), yaw, pitch));
    }

    @Override
    public void setPersistent(boolean persistent) {
        if (entityPersistenceSetter != null) {
            entityPersistenceSetter.setPersistent(get(), persistent);
        }
    }

    @Override
    public boolean isGlowing() {
        if (entityGlowSetter != null) {
            return entityGlowSetter.isGlowing(get());
        }
        return false;
    }

    @Override
    public void setGlowing(boolean glowing) {
        if (entityGlowSetter != null) {
            entityGlowSetter.setGlowing(get(), glowing);
        }
    }

    @Override
    public Vector3dc getPosition() {
        return position;
    }

    @Override
    public EasWorld getWorld() {
        return platform().getWorld(get().getWorld());
    }

    @Override
    public boolean isValid() {
        return get().isValid();
    }

    @Override
    public void remove() {
        get().remove();
    }
}
