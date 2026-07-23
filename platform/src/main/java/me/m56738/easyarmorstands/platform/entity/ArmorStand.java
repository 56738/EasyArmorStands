package me.m56738.easyarmorstands.platform.entity;

import me.m56738.easyarmorstands.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.platform.util.Rotations;

public interface ArmorStand extends LivingEntity {
    Rotations getHeadPose();

    void setHeadPose(Rotations rotations);

    Rotations getBodyPose();

    void setBodyPose(Rotations rotations);

    Rotations getLeftArmPose();

    void setLeftArmPose(Rotations rotations);

    Rotations getRightArmPose();

    void setRightArmPose(Rotations rotations);

    Rotations getLeftLegPose();

    void setLeftLegPose(Rotations rotations);

    Rotations getRightLegPose();

    void setRightLegPose(Rotations rotations);

    boolean isSmall();

    void setSmall(boolean small);

    boolean hasEquipmentLock(EquipmentSlot slot, LockType type);

    void addEquipmentLock(EquipmentSlot slot, LockType type);

    void removeEquipmentLock(EquipmentSlot slot, LockType type);

    boolean hasArms();

    void setArms(boolean arms);

    boolean hasBasePlate();

    void setBasePlate(boolean basePlate);

    boolean isMarker();

    void setMarker(boolean marker);

    boolean isVisible();

    void setVisible(boolean visible);

    boolean canTick();

    void setCanTick(boolean canTick);

    boolean hasGravity();

    void setGravity(boolean gravity);

    boolean isInvulnerable();

    void setInvulnerable(boolean invulnerable);

    enum LockType {
        ADDING_OR_CHANGING,
        REMOVING_OR_CHANGING,
        ADDING,
    }
}
