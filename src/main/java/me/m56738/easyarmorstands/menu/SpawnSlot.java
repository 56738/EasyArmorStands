package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.history.action.EntitySpawnAction;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.session.EntitySpawner;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.joml.Matrix3d;
import org.joml.Vector3d;

public class SpawnSlot<T extends Entity> implements InventorySlot {
    private final SpawnMenu menu;
    private final EntitySpawner<T> spawner;
    private final ItemStack item;

    public SpawnSlot(SpawnMenu menu, EntitySpawner<T> spawner, ItemStack item) {
        this.menu = menu;
        this.spawner = spawner;
        this.item = item;
    }

    @Override
    public void initialize(int slot) {
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ClickType type) {
        if (click) {
            Session session = menu.getSession();
            Player player = session.getPlayer();
            Location eyeLocation = player.getEyeLocation();
            Vector3d cursor = Util.getRotation(eyeLocation, new Matrix3d()).transform(0, 0, 2, new Vector3d());
            Vector3d position = new Vector3d(cursor);
            if (!player.isFlying()) {
                position.y = 0;
            }
            Location location = player.getLocation().add(position.x, position.y, position.z);
            location.setX(session.snap(location.getX()));
            location.setY(session.snap(location.getY()));
            location.setZ(session.snap(location.getZ()));
            location.setYaw((float) session.snapAngle(location.getYaw() + 180));
            location.setPitch((float) session.snapAngle(location.getPitch()));
            T entity = EntitySpawner.trySpawn(spawner, location, player);
            if (entity == null) {
                return false;
            }
            EntitySpawnAction<T> action = new EntitySpawnAction<>(location, spawner, entity.getUniqueId());
            EasyArmorStands.getInstance().getHistory(player).push(action);

            session.selectEntity(entity);
            menu.close(player);
        }
        return false;
    }
}
