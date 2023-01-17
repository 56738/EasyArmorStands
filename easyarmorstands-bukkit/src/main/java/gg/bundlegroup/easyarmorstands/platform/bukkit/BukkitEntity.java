package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasEntity;
import gg.bundlegroup.easyarmorstands.platform.EasWorld;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityGlowSetter;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntityPersistenceSetter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BukkitEntity extends BukkitWrapper implements EasEntity {
    private final BukkitPlatform platform;
    private final Entity entity;
    private final EntityPersistenceSetter entityPersistenceSetter;
    private final EntityGlowSetter entityGlowSetter;
    private final Vector3d position = new Vector3d();

    public BukkitEntity(BukkitPlatform platform, Entity entity) {
        super(platform, entity);
        this.platform = platform;
        this.entity = entity;
        this.entityPersistenceSetter = platform.entityPersistenceSetter();
        this.entityGlowSetter = platform.entityGlowSetter();
    }

    @Override
    public void update() {
        Location location = entity.getLocation();
        position.set(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public void setPersistent(boolean persistent) {
        if (entityPersistenceSetter != null) {
            entityPersistenceSetter.setPersistent(entity, persistent);
        }
    }

    @Override
    public void setGlowing(boolean glowing) {
        if (entityGlowSetter != null) {
            entityGlowSetter.setGlowing(entity, glowing);
        }
    }

    @Override
    public Vector3dc getPosition() {
        return position;
    }

    @Override
    public EasWorld getWorld() {
        return platform.getWorld(entity.getWorld());
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public void remove() {
        entity.remove();
    }
}
