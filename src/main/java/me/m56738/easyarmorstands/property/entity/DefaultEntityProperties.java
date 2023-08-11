package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.capability.glow.GlowCapability;
import me.m56738.easyarmorstands.capability.invulnerability.InvulnerabilityCapability;
import me.m56738.easyarmorstands.capability.lock.LockCapability;
import me.m56738.easyarmorstands.capability.tick.TickCapability;
import me.m56738.easyarmorstands.editor.SimpleEntityObject;
import me.m56738.easyarmorstands.property.PropertyRegistry;
import me.m56738.easyarmorstands.property.armorstand.*;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;

public class DefaultEntityProperties implements Listener {
    private DefaultEntityProperties() {
    }

    public static void registerProperties(SimpleEntityObject entityObject) {
        registerProperties(entityObject.getEntity(), entityObject.properties());
    }

    public static void registerProperties(Entity entity, PropertyRegistry registry) {
        registerEntityProperties(entity, registry);
        if (entity instanceof LivingEntity) {
            registerLivingEntityProperties((LivingEntity) entity, registry);
        }
        if (entity instanceof ArmorStand) {
            registerArmorStandProperties((ArmorStand) entity, registry);
        }
    }

    private static void registerEntityProperties(Entity entity, PropertyRegistry registry) {
        ComponentCapability componentCapability = EasyArmorStands.getInstance().getCapability(ComponentCapability.class);
        GlowCapability glowCapability = EasyArmorStands.getInstance().getCapability(GlowCapability.class);
        if (glowCapability != null) {
            registry.register(new EntityGlowingProperty(entity, glowCapability));
        }
        registry.register(new EntityLocationProperty(entity));
        registry.register(new EntityCustomNameProperty(entity, componentCapability));
        registry.register(new EntityCustomNameVisibleProperty(entity));
    }

    private static void registerLivingEntityProperties(LivingEntity entity, PropertyRegistry registry) {
        EquipmentCapability equipmentCapability = EasyArmorStands.getInstance().getCapability(EquipmentCapability.class);
        ComponentCapability componentCapability = EasyArmorStands.getInstance().getCapability(ComponentCapability.class);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            registry.register(new EntityEquipmentProperty(entity, slot, equipmentCapability, componentCapability));
        }
    }

    private static void registerArmorStandProperties(ArmorStand entity, PropertyRegistry registry) {
        registry.register(new ArmorStandArmsProperty(entity));
        registry.register(new ArmorStandBasePlateProperty(entity));
        registry.register(new ArmorStandMarkerProperty(entity));
        registry.register(new ArmorStandSizeProperty(entity));
        registry.register(new ArmorStandVisibilityProperty(entity));
        TickCapability tickCapability = EasyArmorStands.getInstance().getCapability(TickCapability.class);
        if (tickCapability != null) {
            registry.register(new ArmorStandCanTickProperty(entity, tickCapability));
        }
        registry.register(new ArmorStandGravityProperty(entity));
        InvulnerabilityCapability invulnerabilityCapability = EasyArmorStands.getInstance().getCapability(InvulnerabilityCapability.class);
        if (invulnerabilityCapability != null) {
            registry.register(new ArmorStandInvulnerabilityProperty(entity, invulnerabilityCapability));
        }
        LockCapability lockCapability = EasyArmorStands.getInstance().getCapability(LockCapability.class);
        if (lockCapability != null) {
            registry.register(new ArmorStandLockProperty(entity, lockCapability));
        }
        for (ArmorStandPart part : ArmorStandPart.values()) {
            registry.register(new ArmorStandPoseProperty(entity, part));
        }
    }
}
