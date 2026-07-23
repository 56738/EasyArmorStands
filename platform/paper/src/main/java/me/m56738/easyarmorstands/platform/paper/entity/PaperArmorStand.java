package me.m56738.easyarmorstands.platform.paper.entity;

import me.m56738.easyarmorstands.platform.entity.ArmorStand;
import me.m56738.easyarmorstands.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.platform.paper.PaperAdapter;
import me.m56738.easyarmorstands.platform.util.Rotations;

public interface PaperArmorStand extends ArmorStand, PaperLivingEntity {
    static PaperArmorStand fromNative(org.bukkit.entity.ArmorStand entity) {
        return new PaperArmorStandImpl(entity);
    }

    org.bukkit.entity.ArmorStand getNative();

    static org.bukkit.entity.ArmorStand toNative(ArmorStand armorStand) {
        return ((PaperArmorStand) armorStand).getNative();
    }

    @Override
    default Rotations getHeadPose() {
        return PaperAdapter.fromNative(getNative().getHeadRotations());
    }

    @Override
    default void setHeadPose(Rotations rotations) {
        getNative().setHeadRotations(PaperAdapter.toNative(rotations));
    }

    @Override
    default Rotations getBodyPose() {
        return PaperAdapter.fromNative(getNative().getBodyRotations());
    }

    @Override
    default void setBodyPose(Rotations rotations) {
        getNative().setBodyRotations(PaperAdapter.toNative(rotations));
    }

    @Override
    default Rotations getLeftArmPose() {
        return PaperAdapter.fromNative(getNative().getLeftArmRotations());
    }

    @Override
    default void setLeftArmPose(Rotations rotations) {
        getNative().setLeftArmRotations(PaperAdapter.toNative(rotations));
    }

    @Override
    default Rotations getRightArmPose() {
        return PaperAdapter.fromNative(getNative().getRightArmRotations());
    }

    @Override
    default void setRightArmPose(Rotations rotations) {
        getNative().setRightArmRotations(PaperAdapter.toNative(rotations));
    }

    @Override
    default Rotations getLeftLegPose() {
        return PaperAdapter.fromNative(getNative().getLeftLegRotations());
    }

    @Override
    default void setLeftLegPose(Rotations rotations) {
        getNative().setLeftLegRotations(PaperAdapter.toNative(rotations));
    }

    @Override
    default Rotations getRightLegPose() {
        return PaperAdapter.fromNative(getNative().getRightLegRotations());
    }

    @Override
    default void setRightLegPose(Rotations rotations) {
        getNative().setRightLegRotations(PaperAdapter.toNative(rotations));
    }

    @Override
    default boolean isSmall() {
        return getNative().isSmall();
    }

    @Override
    default void setSmall(boolean small) {
        getNative().setSmall(small);
    }

    @Override
    default boolean hasEquipmentLock(EquipmentSlot slot, LockType type) {
        return getNative().hasEquipmentLock(PaperAdapter.toNative(slot), PaperAdapter.toNative(type));
    }

    @Override
    default void addEquipmentLock(EquipmentSlot slot, LockType type) {
        getNative().addEquipmentLock(PaperAdapter.toNative(slot), PaperAdapter.toNative(type));
    }

    @Override
    default void removeEquipmentLock(EquipmentSlot slot, LockType type) {
        getNative().removeEquipmentLock(PaperAdapter.toNative(slot), PaperAdapter.toNative(type));
    }

    @Override
    default boolean hasArms() {
        return getNative().hasArms();
    }

    @Override
    default void setArms(boolean arms) {
        getNative().setArms(arms);
    }

    @Override
    default boolean hasBasePlate() {
        return getNative().hasBasePlate();
    }

    @Override
    default void setBasePlate(boolean basePlate) {
        getNative().setBasePlate(basePlate);
    }

    @Override
    default boolean isMarker() {
        return getNative().isMarker();
    }

    @Override
    default void setMarker(boolean marker) {
        getNative().setMarker(marker);
    }

    @Override
    default boolean isVisible() {
        return getNative().isVisible();
    }

    @Override
    default void setVisible(boolean visible) {
        getNative().setVisible(visible);
    }

    @Override
    default boolean canTick() {
        return getNative().canTick();
    }

    @Override
    default void setCanTick(boolean canTick) {
        getNative().setCanTick(canTick);
    }

    @Override
    default boolean hasGravity() {
        return getNative().hasGravity();
    }

    @Override
    default void setGravity(boolean gravity) {
        getNative().setGravity(gravity);
    }

    @Override
    default boolean isInvulnerable() {
        return getNative().isInvulnerable();
    }

    @Override
    default void setInvulnerable(boolean invulnerable) {
        getNative().setInvulnerable(invulnerable);
    }
}
