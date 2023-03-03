package me.m56738.easyarmorstands.core.session;

import me.m56738.easyarmorstands.core.inventory.SessionMenu;
import me.m56738.easyarmorstands.core.platform.EasArmorStand;
import me.m56738.easyarmorstands.core.platform.EasFeature;
import me.m56738.easyarmorstands.core.platform.EasPlayer;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandSession extends Session {
    private final EasArmorStand entity;
    private final EasArmorStand skeleton;
    private final Matrix3d armorStandYaw = new Matrix3d();

    public ArmorStandSession(EasPlayer player, EasArmorStand entity) {
        super(player);
        this.entity = entity;
        if (player.platform().hasFeature(EasFeature.ENTITY_GLOW)) {
            this.skeleton = entity.getWorld().spawnArmorStand(entity.getPosition(), entity.getYaw(), e -> {
                e.setVisible(false);
                e.setBasePlate(false);
                e.setArms(true);
                e.setPersistent(false);
                e.setGravity(false);
                e.setCanTick(false);
                updateSkeleton(e);
                for (EasPlayer other : player.platform().getPlayers()) {
                    if (!player.equals(other)) {
                        other.hideEntity(e);
                    }
                }
                e.setGlowing(true);
            });
        } else {
            this.skeleton = null;
        }
    }

    @Override
    public boolean update() {
        entity.update();
        armorStandYaw.rotationY(-Math.toRadians(entity.getYaw()));

        boolean result = super.update();

        if (skeleton != null) {
            updateSkeleton(skeleton);
        }

        return result && entity.isValid() && (skeleton == null || skeleton.isValid()) &&
                getPlayer().getEyePosition().distanceSquared(entity.getPosition()) < 100 * 100;
    }

    @Override
    public void stop() {
        if (skeleton != null) {
            skeleton.remove();
        }
        super.stop();
    }

    @Override
    public void startMoving(Vector3dc cursor) {
        entity.update();
        super.startMoving(cursor);
    }

    private void updateSkeleton(EasArmorStand skeleton) {
        skeleton.teleport(entity.getPosition(), entity.getYaw(), 0);
        skeleton.setSmall(entity.isSmall());
        Vector3d pose = new Vector3d();
        for (EasArmorStand.Part part : EasArmorStand.Part.values()) {
            skeleton.setPose(part, entity.getPose(part, pose));
        }
    }

    public void hideSkeleton(EasPlayer player) {
        if (skeleton != null) {
            player.hideEntity(skeleton);
        }
    }

    @Override
    protected void onLeftClick() {
        if (getPlayer().hasPermission("easyarmorstands.open")) {
            openMenu();
        }
    }

    public void openMenu() {
        SessionMenu inventory = new SessionMenu(this, entity.platform());
        getPlayer().openInventory(inventory.getInventory());
    }

    public EasArmorStand getEntity() {
        return entity;
    }

    public Matrix3dc getArmorStandYaw() {
        return armorStandYaw;
    }

    public boolean canMove(Vector3dc position) {
        return entity.platform().canMoveSession(this, entity.getWorld(), position);
    }

    public boolean move(Vector3dc position) {
        return move(position, entity.getYaw());
    }

    public boolean move(Vector3dc position, float yaw) {
        return entity.platform().canMoveSession(this, entity.getWorld(), position) && entity.teleport(position, yaw, 0);
    }
}
