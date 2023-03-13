package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.capability.glow.GlowCapability;
import me.m56738.easyarmorstands.capability.spawn.SpawnCapability;
import me.m56738.easyarmorstands.capability.tick.TickCapability;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.util.EnumMap;
import java.util.Objects;

public class ArmorStandSnapshot {
    private final Location location;
    private final boolean visible;
    private final boolean basePlate;
    private final boolean arms;
    private final boolean gravity;
    private final boolean small;
    private final boolean glow;
    private final Component customName;
    private final boolean customNameVisible;
    private final boolean canTick;
    private final EnumMap<ArmorStandPart, EulerAngle> poses;
    private final EnumMap<EquipmentSlot, ItemStack> items;

    public ArmorStandSnapshot(ArmorStand entity) {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        ComponentCapability componentCapability = plugin.getCapability(ComponentCapability.class);
        EquipmentCapability equipmentCapability = plugin.getCapability(EquipmentCapability.class);
        GlowCapability glowCapability = plugin.getCapability(GlowCapability.class);
        TickCapability tickCapability = plugin.getCapability(TickCapability.class);
        location = entity.getLocation();
        visible = entity.isVisible();
        basePlate = entity.hasBasePlate();
        arms = entity.hasArms();
        gravity = entity.hasGravity();
        small = entity.isSmall();
        glow = glowCapability != null && glowCapability.isGlowing(entity);
        customName = componentCapability.getCustomName(entity);
        customNameVisible = entity.isCustomNameVisible();
        canTick = tickCapability == null || tickCapability.canTick(entity);
        poses = new EnumMap<>(ArmorStandPart.class);
        for (ArmorStandPart part : ArmorStandPart.values()) {
            poses.put(part, part.getPose(entity));
        }
        items = new EnumMap<>(EquipmentSlot.class);
        EntityEquipment equipment = entity.getEquipment();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            items.put(slot, equipmentCapability.getItem(equipment, slot));
        }
    }

    public Location getLocation() {
        return location;
    }

    public void apply(ArmorStand entity) {
        entity.teleport(location);
        applyProperties(entity);
    }

    public ArmorStand spawn() {
        return EasyArmorStands.getInstance().getCapability(SpawnCapability.class)
                .spawnEntity(location, ArmorStand.class, this::applyProperties);
    }

    private void applyProperties(ArmorStand entity) {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        ComponentCapability componentCapability = plugin.getCapability(ComponentCapability.class);
        EquipmentCapability equipmentCapability = plugin.getCapability(EquipmentCapability.class);
        GlowCapability glowCapability = plugin.getCapability(GlowCapability.class);
        TickCapability tickCapability = plugin.getCapability(TickCapability.class);
        entity.setVisible(visible);
        entity.setBasePlate(basePlate);
        entity.setArms(arms);
        entity.setGravity(gravity);
        entity.setSmall(small);
        componentCapability.setCustomName(entity, customName);
        entity.setCustomNameVisible(customNameVisible);
        if (tickCapability != null) {
            tickCapability.setCanTick(entity, canTick);
        }
        poses.forEach((part, angle) -> part.setPose(entity, angle));
        EntityEquipment equipment = entity.getEquipment();
        items.forEach((slot, item) -> equipmentCapability.setItem(equipment, slot, item));
        if (glowCapability != null) {
            glowCapability.setGlowing(entity, glow);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArmorStandSnapshot that = (ArmorStandSnapshot) o;
        return visible == that.visible && basePlate == that.basePlate && arms == that.arms && gravity == that.gravity && small == that.small && glow == that.glow && customNameVisible == that.customNameVisible && canTick == that.canTick && location.equals(that.location) && Objects.equals(customName, that.customName) && poses.equals(that.poses) && items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, visible, basePlate, arms, gravity, small, glow, customName, customNameVisible, canTick, poses, items);
    }
}
