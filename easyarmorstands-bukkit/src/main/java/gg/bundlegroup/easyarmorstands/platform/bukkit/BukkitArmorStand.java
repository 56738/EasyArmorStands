package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BukkitArmorStand extends BukkitEntity implements EasArmorStand {
    private final ArmorStand armorStand;

    public BukkitArmorStand(BukkitPlatform platform, ArmorStand armorStand) {
        super(platform, armorStand);
        this.armorStand = armorStand;
    }

    @Override
    public void setVisible(boolean visible) {
        armorStand.setVisible(visible);
    }

    @Override
    public void setBasePlate(boolean basePlate) {
        armorStand.setBasePlate(basePlate);
    }

    @Override
    public void setArms(boolean arms) {
        armorStand.setArms(arms);
    }

    @Override
    public void setGravity(boolean gravity) {
        armorStand.setGravity(gravity);
    }

    @Override
    public boolean isSmall() {
        return armorStand.isSmall();
    }

    @Override
    public float getYaw() {
        return armorStand.getLocation().getYaw();
    }

    private EulerAngle getAngle(Part part) {
        switch (part) {
            case HEAD:
                return armorStand.getHeadPose();
            case BODY:
                return armorStand.getBodyPose();
            case LEFT_ARM:
                return armorStand.getLeftArmPose();
            case RIGHT_ARM:
                return armorStand.getRightArmPose();
            case LEFT_LEG:
                return armorStand.getLeftLegPose();
            case RIGHT_LEG:
                return armorStand.getRightLegPose();
            default:
                throw new IllegalArgumentException();
        }
    }

    private void setAngle(Part part, EulerAngle angle) {
        switch (part) {
            case HEAD:
                armorStand.setHeadPose(angle);
                break;
            case BODY:
                armorStand.setBodyPose(angle);
                break;
            case LEFT_ARM:
                armorStand.setLeftArmPose(angle);
                break;
            case RIGHT_ARM:
                armorStand.setRightArmPose(angle);
                break;
            case LEFT_LEG:
                armorStand.setLeftLegPose(angle);
                break;
            case RIGHT_LEG:
                armorStand.setRightLegPose(angle);
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public Vector3d getPose(Part part, Vector3d dest) {
        EulerAngle angle = getAngle(part);
        dest.set(angle.getX(), angle.getY(), angle.getZ());
        return dest;
    }

    @Override
    public void setPose(Part part, Vector3dc pose) {
        setAngle(part, new EulerAngle(pose.x(), pose.y(), pose.z()));
    }
}
