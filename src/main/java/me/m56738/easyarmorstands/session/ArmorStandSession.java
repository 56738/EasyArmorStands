package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.glow.GlowCapability;
import me.m56738.easyarmorstands.capability.persistence.PersistenceCapability;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import me.m56738.easyarmorstands.capability.tick.TickCapability;
import me.m56738.easyarmorstands.capability.visibility.VisibilityCapability;
import me.m56738.easyarmorstands.event.SessionMoveEvent;
import me.m56738.easyarmorstands.history.EditArmorStandAction;
import me.m56738.easyarmorstands.menu.SessionMenu;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.ArmorStandSnapshot;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3dc;

import java.util.Objects;

public class ArmorStandSession extends Session {
    private final ArmorStand entity;
    private final ArmorStand skeleton;
    private final Matrix3d armorStandYaw = new Matrix3d();
    private ArmorStandSnapshot lastSnapshot;

    public ArmorStandSession(Player player, ArmorStand entity) {
        super(player);
        this.entity = entity;
        this.lastSnapshot = new ArmorStandSnapshot(entity);
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        GlowCapability glowCapability = plugin.getCapability(GlowCapability.class);
        if (glowCapability != null) {
            SpawnCapability spawnCapability = plugin.getCapability(SpawnCapability.class);
            this.skeleton = spawnCapability.spawnEntity(entity.getLocation(), ArmorStand.class, e -> {
                e.setVisible(false);
                e.setBasePlate(false);
                e.setArms(true);
                e.setGravity(false);
                PersistenceCapability persistenceCapability = plugin.getCapability(PersistenceCapability.class);
                if (persistenceCapability != null) {
                    persistenceCapability.setPersistent(e, false);
                }
                TickCapability tickCapability = plugin.getCapability(TickCapability.class);
                if (tickCapability != null) {
                    tickCapability.setCanTick(e, false);
                }
                updateSkeleton(e);
                VisibilityCapability visibilityCapability = plugin.getCapability(VisibilityCapability.class);
                if (visibilityCapability != null) {
                    for (Player other : Bukkit.getOnlinePlayers()) {
                        if (player != other) {
                            visibilityCapability.hideEntity(other, plugin, e);
                        }
                    }
                }
                glowCapability.setGlowing(e, true);
            });
        } else {
            this.skeleton = null;
        }
    }

    @Override
    public void commit() {
        ArmorStandSnapshot snapshot = new ArmorStandSnapshot(entity);
        if (Objects.equals(lastSnapshot, snapshot)) {
            return;
        }
        EasyArmorStands.getInstance().getHistory(getPlayer()).push(new EditArmorStandAction(
                lastSnapshot,
                snapshot,
                entity.getUniqueId()));
        lastSnapshot = snapshot;
    }

    @Override
    public boolean update() {
        armorStandYaw.rotationY(-Math.toRadians(entity.getLocation().getYaw()));

        boolean result = super.update();

        if (skeleton != null) {
            updateSkeleton(skeleton);
        }

        return result && entity.isValid() && (skeleton == null || skeleton.isValid()) &&
                getPlayer().getEyeLocation().distanceSquared(entity.getLocation()) < 100 * 100;
    }

    @Override
    public void stop() {
        if (skeleton != null) {
            skeleton.remove();
        }
        super.stop();
    }

    private void updateSkeleton(ArmorStand skeleton) {
        skeleton.teleport(entity.getLocation());
        skeleton.setSmall(entity.isSmall());
        for (ArmorStandPart part : ArmorStandPart.values()) {
            part.setPose(skeleton, part.getPose(entity));
        }
    }

    public void hideSkeleton(Player player) {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        VisibilityCapability visibilityCapability = plugin.getCapability(VisibilityCapability.class);
        if (skeleton != null && visibilityCapability != null) {
            visibilityCapability.hideEntity(player, plugin, skeleton);
        }
    }

    @Override
    protected void onLeftClick() {
        if (getPlayer().hasPermission("easyarmorstands.open")) {
            openMenu();
        }
    }

    public void openMenu() {
        SessionMenu inventory = new SessionMenu(this);
        getPlayer().openInventory(inventory.getInventory());
    }

    public ArmorStand getEntity() {
        return entity;
    }

    public Matrix3dc getArmorStandYaw() {
        return armorStandYaw;
    }

    public boolean canMove(Vector3dc position) {
        SessionMoveEvent event = new SessionMoveEvent(this, entity.getWorld(), position);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public Vector3dc getPosition() {
        return Util.toVector3d(entity.getLocation());
    }

    public Matrix3dc getRotation() {
        return armorStandYaw;
    }

    public boolean move(Vector3dc position) {
        return move(position, entity.getLocation().getYaw());
    }

    public boolean move(Vector3dc position, float yaw) {
        return canMove(position) &&
                entity.teleport(new Location(entity.getWorld(), position.x(), position.y(), position.z(), yaw, 0));
    }
}
