package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.element.ConfigurableEntityElement;
import me.m56738.easyarmorstands.api.event.element.EntityElementInitializeEvent;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.capability.entityscale.EntityScaleCapability;
import me.m56738.easyarmorstands.capability.entitytag.EntityTagCapability;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.capability.glow.GlowCapability;
import me.m56738.easyarmorstands.capability.invulnerability.InvulnerabilityCapability;
import me.m56738.easyarmorstands.capability.lock.LockCapability;
import me.m56738.easyarmorstands.capability.silent.SilentCapability;
import me.m56738.easyarmorstands.capability.tick.TickCapability;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandArmsProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandBasePlateProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandCanTickProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandGravityProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandInvulnerabilityProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandLockProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandMarkerProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandPoseProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandSizeProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandVisibilityProperty;
import me.m56738.easyarmorstands.property.entity.EntityCustomNameProperty;
import me.m56738.easyarmorstands.property.entity.EntityCustomNameVisibleProperty;
import me.m56738.easyarmorstands.property.entity.EntityEquipmentProperty;
import me.m56738.easyarmorstands.property.entity.EntityGlowingProperty;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.property.entity.EntityScaleProperty;
import me.m56738.easyarmorstands.property.entity.EntitySilentProperty;
import me.m56738.easyarmorstands.property.entity.EntityTagsProperty;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;

public class EntityElementListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInitialize(EntityElementInitializeEvent event) {
        ConfigurableEntityElement<?> element = event.getElement();
        registerProperties(element.getEntity(), element.getProperties());
    }

    private void registerProperties(Entity entity, PropertyRegistry registry) {
        registerEntityProperties(entity, registry);
        if (entity instanceof LivingEntity) {
            registerLivingEntityProperties((LivingEntity) entity, registry);
        }
        if (entity instanceof ArmorStand) {
            registerArmorStandProperties((ArmorStand) entity, registry);
        }
    }

    private void registerEntityProperties(Entity entity, PropertyRegistry registry) {
        ComponentCapability componentCapability = EasyArmorStandsPlugin.getInstance().getCapability(ComponentCapability.class);
        GlowCapability glowCapability = EasyArmorStandsPlugin.getInstance().getCapability(GlowCapability.class);
        if (glowCapability != null) {
            registry.register(new EntityGlowingProperty(entity, glowCapability));
        }
        registry.register(new EntityLocationProperty(entity));
        SilentCapability silentCapability = EasyArmorStandsPlugin.getInstance().getCapability(SilentCapability.class);
        if (silentCapability != null) {
            registry.register(new EntitySilentProperty(entity, silentCapability));
        }
        EntityTagCapability tagCapability = EasyArmorStandsPlugin.getInstance().getCapability(EntityTagCapability.class);
        if (tagCapability != null) {
            registry.register(new EntityTagsProperty(entity, tagCapability));
        }
        registry.register(new EntityCustomNameProperty(entity, componentCapability));
        registry.register(new EntityCustomNameVisibleProperty(entity));
    }

    private void registerLivingEntityProperties(LivingEntity entity, PropertyRegistry registry) {
        EquipmentCapability equipmentCapability = EasyArmorStandsPlugin.getInstance().getCapability(EquipmentCapability.class);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            registry.register(new EntityEquipmentProperty(entity, slot, equipmentCapability));
        }
        EntityScaleCapability scaleCapability = EasyArmorStandsPlugin.getInstance().getCapability(EntityScaleCapability.class);
        if (scaleCapability != null) {
            registry.register(new EntityScaleProperty(entity, scaleCapability));
        }
    }

    private void registerArmorStandProperties(ArmorStand entity, PropertyRegistry registry) {
        registry.register(new ArmorStandArmsProperty(entity));
        registry.register(new ArmorStandBasePlateProperty(entity));
        registry.register(new ArmorStandMarkerProperty(entity));
        registry.register(new ArmorStandSizeProperty(entity));
        registry.register(new ArmorStandVisibilityProperty(entity));
        TickCapability tickCapability = EasyArmorStandsPlugin.getInstance().getCapability(TickCapability.class);
        if (tickCapability != null) {
            registry.register(new ArmorStandCanTickProperty(entity, tickCapability));
        }
        registry.register(new ArmorStandGravityProperty(entity));
        InvulnerabilityCapability invulnerabilityCapability = EasyArmorStandsPlugin.getInstance().getCapability(InvulnerabilityCapability.class);
        if (invulnerabilityCapability != null) {
            registry.register(new ArmorStandInvulnerabilityProperty(entity, invulnerabilityCapability));
        }
        LockCapability lockCapability = EasyArmorStandsPlugin.getInstance().getCapability(LockCapability.class);
        if (lockCapability != null) {
            registry.register(new ArmorStandLockProperty(entity, lockCapability));
        }
        for (ArmorStandPart part : ArmorStandPart.values()) {
            registry.register(new ArmorStandPoseProperty(entity, part));
        }
    }
}
