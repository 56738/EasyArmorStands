package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BukkitArmorStand extends BukkitArmorEntity<ArmorStand> implements EasArmorStand {
    public BukkitArmorStand(BukkitPlatform platform, ArmorStand armorStand) {
        super(platform, armorStand);
    }

    @Override
    public boolean isVisible() {
        return get().isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        get().setVisible(visible);
    }

    @Override
    public boolean hasBasePlate() {
        return get().hasBasePlate();
    }

    @Override
    public void setBasePlate(boolean basePlate) {
        get().setBasePlate(basePlate);
    }

    @Override
    public boolean hasArms() {
        return get().hasArms();
    }

    @Override
    public void setArms(boolean arms) {
        get().setArms(arms);
    }

    @Override
    public boolean hasGravity() {
        return get().hasGravity();
    }

    @Override
    public void setGravity(boolean gravity) {
        get().setGravity(gravity);
    }

    @Override
    public boolean isSmall() {
        return get().isSmall();
    }

    @Override
    public void setSmall(boolean small) {
        get().setSmall(small);
    }

    @Override
    public float getYaw() {
        return get().getLocation().getYaw();
    }

    private EulerAngle getAngle(Part part) {
        switch (part) {
            case HEAD:
                return get().getHeadPose();
            case BODY:
                return get().getBodyPose();
            case LEFT_ARM:
                return get().getLeftArmPose();
            case RIGHT_ARM:
                return get().getRightArmPose();
            case LEFT_LEG:
                return get().getLeftLegPose();
            case RIGHT_LEG:
                return get().getRightLegPose();
            default:
                throw new IllegalArgumentException();
        }
    }

    private void setAngle(Part part, EulerAngle angle) {
        switch (part) {
            case HEAD:
                get().setHeadPose(angle);
                break;
            case BODY:
                get().setBodyPose(angle);
                break;
            case LEFT_ARM:
                get().setLeftArmPose(angle);
                break;
            case RIGHT_ARM:
                get().setRightArmPose(angle);
                break;
            case LEFT_LEG:
                get().setLeftLegPose(angle);
                break;
            case RIGHT_LEG:
                get().setRightLegPose(angle);
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
