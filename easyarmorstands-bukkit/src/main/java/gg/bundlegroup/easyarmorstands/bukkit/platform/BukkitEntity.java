package gg.bundlegroup.easyarmorstands.bukkit.platform;

import gg.bundlegroup.easyarmorstands.bukkit.feature.EntityGlowSetter;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EntityInvulnerableAccessor;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EntityNameAccessor;
import gg.bundlegroup.easyarmorstands.bukkit.feature.EntityPersistenceSetter;
import gg.bundlegroup.easyarmorstands.common.platform.EasEntity;
import gg.bundlegroup.easyarmorstands.common.platform.EasWorld;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BukkitEntity<T extends Entity> extends BukkitWrapper<T> implements EasEntity {
    private final EntityPersistenceSetter entityPersistenceSetter;
    private final EntityGlowSetter entityGlowSetter;
    private final EntityInvulnerableAccessor entityInvulnerableAccessor;
    private final EntityNameAccessor entityNameAccessor;
    private final Vector3d position = new Vector3d();

    public BukkitEntity(BukkitPlatform platform, T entity) {
        super(platform, entity);
        this.entityPersistenceSetter = platform.entityPersistenceSetter();
        this.entityGlowSetter = platform.entityGlowSetter();
        this.entityInvulnerableAccessor = platform.entityInvulnerableAccessor();
        this.entityNameAccessor = platform.entityNameAccessor();
    }

    @Override
    public void update() {
        Location location = get().getLocation();
        position.set(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public void teleport(Vector3dc position, float yaw, float pitch) {
        get().teleport(new Location(get().getWorld(), position.x(), position.y(), position.z(), yaw, pitch));
        update();
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
    public boolean isInvulnerable() {
        if (entityInvulnerableAccessor == null) {
            return false;
        }
        return entityInvulnerableAccessor.isInvulnerable(get());
    }

    @Override
    public void setInvulnerable(boolean invulnerable) {
        if (entityInvulnerableAccessor == null) {
            return;
        }
        entityInvulnerableAccessor.setInvulnerable(get(), invulnerable);
    }

    @Override
    public Component getCustomName() {
        return entityNameAccessor.getName(get());
    }

    @Override
    public void setCustomName(Component customName) {
        entityNameAccessor.setName(get(), customName);
    }

    @Override
    public boolean isCustomNameVisible() {
        return get().isCustomNameVisible();
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        get().setCustomNameVisible(visible);
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
