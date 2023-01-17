package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.platform.EasWorld;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.joml.Vector3dc;

import java.util.function.Consumer;

public class BukkitWorld extends BukkitWrapper implements EasWorld {
    private final BukkitPlatform platform;
    private final World world;

    public BukkitWorld(BukkitPlatform platform, World world) {
        super(platform, world);
        this.platform = platform;
        this.world = world;
    }

    protected <T extends Entity> T spawnEntity(Location location, Class<T> type, Consumer<T> configure) {
        T entity = world.spawn(location, type);
        configure.accept(entity);
        return entity;
    }

    @Override
    public EasArmorStand spawnArmorStand(Vector3dc position, float yaw, Consumer<EasArmorStand> configure) {
        Location location = new Location(world, position.x(), position.y(), position.z(), yaw, 0);
        ArmorStand entity = spawnEntity(location, ArmorStand.class, e -> configure.accept(platform.getArmorStand(e)));
        return platform.getArmorStand(entity);
    }
}
