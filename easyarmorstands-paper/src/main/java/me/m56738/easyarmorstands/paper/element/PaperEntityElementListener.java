package me.m56738.easyarmorstands.paper.element;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.element.DefaultEntityElement;
import me.m56738.easyarmorstands.api.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.paper.api.event.element.EntityElementInitializeEvent;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperEntity;
import me.m56738.easyarmorstands.paper.property.armorstand.ArmorStandArmsProperty;
import me.m56738.easyarmorstands.paper.property.armorstand.ArmorStandBasePlateProperty;
import me.m56738.easyarmorstands.paper.property.armorstand.ArmorStandCanTickProperty;
import me.m56738.easyarmorstands.paper.property.armorstand.ArmorStandGravityProperty;
import me.m56738.easyarmorstands.paper.property.armorstand.ArmorStandInvulnerabilityProperty;
import me.m56738.easyarmorstands.paper.property.armorstand.ArmorStandLockProperty;
import me.m56738.easyarmorstands.paper.property.armorstand.ArmorStandMarkerProperty;
import me.m56738.easyarmorstands.paper.property.armorstand.ArmorStandPoseProperty;
import me.m56738.easyarmorstands.paper.property.armorstand.ArmorStandSizeProperty;
import me.m56738.easyarmorstands.paper.property.armorstand.ArmorStandVisibilityProperty;
import me.m56738.easyarmorstands.paper.property.entity.EntityAIProperty;
import me.m56738.easyarmorstands.paper.property.entity.EntityCustomNameProperty;
import me.m56738.easyarmorstands.paper.property.entity.EntityCustomNameVisibleProperty;
import me.m56738.easyarmorstands.paper.property.entity.EntityEquipmentProperty;
import me.m56738.easyarmorstands.paper.property.entity.EntityGlowingProperty;
import me.m56738.easyarmorstands.paper.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.paper.property.entity.EntityScaleProperty;
import me.m56738.easyarmorstands.paper.property.entity.EntitySilentProperty;
import me.m56738.easyarmorstands.paper.property.entity.EntityTagsProperty;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PaperEntityElementListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInitialize(EntityElementInitializeEvent event) {
        DefaultEntityElement element = event.getElement();
        registerProperties(PaperEntity.toNative(element.getEntity()), element.getProperties());
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
        registry.register(new EntityGlowingProperty(entity));
        registry.register(new EntityLocationProperty(entity));
        registry.register(new EntitySilentProperty(entity));
        registry.register(new EntityTagsProperty(entity));
        registry.register(new EntityCustomNameProperty(entity));
        registry.register(new EntityCustomNameVisibleProperty(entity));
    }

    private void registerLivingEntityProperties(LivingEntity entity, PropertyRegistry registry) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (entity instanceof ArmorStand && (slot == EquipmentSlot.BODY || slot == EquipmentSlot.SADDLE)) {
                continue;
            }
            registry.register(new EntityEquipmentProperty(entity, slot));
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
}
