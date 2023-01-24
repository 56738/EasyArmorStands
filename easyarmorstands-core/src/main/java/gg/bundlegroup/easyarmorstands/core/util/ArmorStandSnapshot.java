package gg.bundlegroup.easyarmorstands.core.util;

import gg.bundlegroup.easyarmorstands.core.platform.EasArmorEntity;
import gg.bundlegroup.easyarmorstands.core.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.core.platform.EasItem;
import gg.bundlegroup.easyarmorstands.core.platform.EasWorld;
import net.kyori.adventure.text.Component;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.EnumMap;
import java.util.Objects;

public class ArmorStandSnapshot {
    private final Vector3dc position;
    private final float yaw;
    private final boolean visible;
    private final boolean basePlate;
    private final boolean arms;
    private final boolean gravity;
    private final boolean small;
    private final boolean glow;
    private final Component customName;
    private final boolean customNameVisible;
    private final boolean canTick;
    private final EnumMap<EasArmorStand.Part, Vector3dc> poses;
    private final EnumMap<EasArmorEntity.Slot, EasItem> items;

    public ArmorStandSnapshot(EasArmorStand entity) {
        position = new Vector3d(entity.getPosition());
        yaw = entity.getYaw();
        visible = entity.isVisible();
        basePlate = entity.hasBasePlate();
        arms = entity.hasArms();
        gravity = entity.hasGravity();
        small = entity.isSmall();
        glow = entity.isGlowing();
        customName = entity.getCustomName();
        customNameVisible = entity.isCustomNameVisible();
        canTick = entity.canTick();
        poses = new EnumMap<>(EasArmorStand.Part.class);
        for (EasArmorStand.Part part : EasArmorStand.Part.values()) {
            poses.put(part, entity.getPose(part, new Vector3d()));
        }
        items = new EnumMap<>(EasArmorEntity.Slot.class);
        for (EasArmorEntity.Slot slot : EasArmorEntity.Slot.values()) {
            items.put(slot, entity.getItem(slot));
        }
    }

    public void apply(EasArmorStand entity) {
        entity.teleport(position, yaw, 0);
        applyProperties(entity);
    }

    public EasArmorStand spawn(EasWorld world) {
        return world.spawnArmorStand(position, yaw, this::applyProperties);
    }

    private void applyProperties(EasArmorStand entity) {
        entity.setVisible(visible);
        entity.setBasePlate(basePlate);
        entity.setArms(arms);
        entity.setGravity(gravity);
        entity.setSmall(small);
        entity.setCustomName(customName);
        entity.setCustomNameVisible(customNameVisible);
        entity.setCanTick(canTick);
        poses.forEach(entity::setPose);
        items.forEach(entity::setItem);
        entity.setGlowing(glow);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArmorStandSnapshot that = (ArmorStandSnapshot) o;
        return Float.compare(that.yaw, yaw) == 0 &&
                visible == that.visible &&
                basePlate == that.basePlate &&
                arms == that.arms &&
                gravity == that.gravity &&
                small == that.small &&
                glow == that.glow &&
                customNameVisible == that.customNameVisible &&
                canTick == that.canTick &&
                position.equals(that.position) &&
                Objects.equals(customName, that.customName) &&
                poses.equals(that.poses) &&
                items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, yaw, visible, basePlate, arms, gravity, small, glow,
                customName, customNameVisible, canTick, poses, items);
    }
}
