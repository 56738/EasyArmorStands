package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.element.ConfigurableEntityElement;
import me.m56738.easyarmorstands.api.event.element.EntityElementInitializeEvent;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
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
import me.m56738.easyarmorstands.property.entity.EntityAIProperty;
import me.m56738.easyarmorstands.property.entity.EntityCustomNameProperty;
import me.m56738.easyarmorstands.property.entity.EntityCustomNameVisibleProperty;
import me.m56738.easyarmorstands.property.entity.EntityEquipmentProperty;
import me.m56738.easyarmorstands.property.entity.EntityGlowingProperty;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.property.entity.EntityScaleProperty;
import me.m56738.easyarmorstands.property.entity.EntitySilentProperty;
import me.m56738.easyarmorstands.property.entity.EntityTagsProperty;
import me.m56738.easyarmorstands.property.mannequin.MannequinDescriptionProperty;
import me.m56738.easyarmorstands.property.mannequin.MannequinImmovableProperty;
import me.m56738.easyarmorstands.property.mannequin.MannequinMainHandProperty;
import me.m56738.easyarmorstands.property.mannequin.MannequinProfileProperty;
import me.m56738.easyarmorstands.property.mannequin.part.MannequinCapeVisibleProperty;
import me.m56738.easyarmorstands.property.mannequin.part.MannequinHatVisibleProperty;
import me.m56738.easyarmorstands.property.mannequin.part.MannequinJacketVisibleProperty;
import me.m56738.easyarmorstands.property.mannequin.part.MannequinLeftPantsVisibleProperty;
import me.m56738.easyarmorstands.property.mannequin.part.MannequinLeftSleeveVisibleProperty;
import me.m56738.easyarmorstands.property.mannequin.part.MannequinRightPantsVisibleProperty;
import me.m56738.easyarmorstands.property.mannequin.part.MannequinRightSleeveVisibleProperty;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mannequin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EntityEquipment;
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
        if (entity instanceof Mannequin) {
            registerMannequinProperties((Mannequin) entity, registry);
        }
    }

    private void registerEntityProperties(Entity entity, PropertyRegistry registry) {
        registry.register(new EntityGlowingProperty(entity));
        registry.register(new EntityLocationProperty(entity));
        registry.register(new EntitySilentProperty(entity));
        registry.register(new EntityTagsProperty(entity));
        registry.register(new EntityCustomNameProperty(entity));
        registry.register(new EntityCustomNameVisibleProperty(entity));
    }

    private void registerLivingEntityProperties(LivingEntity entity, PropertyRegistry registry) {
        EntityEquipment equipment = entity.getEquipment();
        if (equipment != null) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                registry.register(new EntityEquipmentProperty(equipment, slot));
            }
        }
        registry.register(new EntityScaleProperty(entity));
        registry.register(new EntityAIProperty(entity));
    }

    private void registerArmorStandProperties(ArmorStand entity, PropertyRegistry registry) {
        registry.register(new ArmorStandArmsProperty(entity));
        registry.register(new ArmorStandBasePlateProperty(entity));
        registry.register(new ArmorStandMarkerProperty(entity));
        registry.register(new ArmorStandSizeProperty(entity));
        registry.register(new ArmorStandVisibilityProperty(entity));
        registry.register(new ArmorStandCanTickProperty(entity));
        registry.register(new ArmorStandGravityProperty(entity));
        registry.register(new ArmorStandInvulnerabilityProperty(entity));
        registry.register(new ArmorStandLockProperty(entity));
        for (ArmorStandPart part : ArmorStandPart.values()) {
            registry.register(new ArmorStandPoseProperty(entity, part));
        }
    }

    private void registerMannequinProperties(Mannequin entity, PropertyRegistry registry) {
        registry.register(new MannequinMainHandProperty(entity));
        registry.register(new MannequinProfileProperty(entity));
        registry.register(new MannequinImmovableProperty(entity));
        registry.register(new MannequinDescriptionProperty(entity));
        registry.register(new MannequinCapeVisibleProperty(entity));
        registry.register(new MannequinJacketVisibleProperty(entity));
        registry.register(new MannequinLeftSleeveVisibleProperty(entity));
        registry.register(new MannequinRightSleeveVisibleProperty(entity));
        registry.register(new MannequinLeftPantsVisibleProperty(entity));
        registry.register(new MannequinRightPantsVisibleProperty(entity));
        registry.register(new MannequinHatVisibleProperty(entity));
    }
}
