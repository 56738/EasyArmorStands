package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.platform.EasWorld;
import gg.bundlegroup.easyarmorstands.platform.bukkit.feature.EntitySpawner;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.joml.Vector3dc;

import java.util.function.Consumer;

public class BukkitWorld extends BukkitWrapper<World> implements EasWorld {
    private final EntitySpawner entitySpawner;

    public BukkitWorld(BukkitPlatform platform, World world) {
        super(platform, world);
        this.entitySpawner = platform.entitySpawner();
    }

    @Override
    public EasArmorStand spawnArmorStand(Vector3dc position, float yaw, Consumer<EasArmorStand> configure) {
        Location location = new Location(get(), position.x(), position.y(), position.z(), yaw, 0);
        EasArmorStand entity;
        if (entitySpawner != null) {
            entity = platform().getArmorStand(entitySpawner.spawnEntity(
                    location, ArmorStand.class,
                    e -> configure.accept(platform().getArmorStand(e))));
        } else {
            entity = platform().getArmorStand(get().spawn(location, ArmorStand.class));
            configure.accept(entity);
        }
        return entity;
    }
}
