package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.element.ConfigurableEntityElement;
import me.m56738.easyarmorstands.api.event.element.EntityElementInitializeEvent;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandLockProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandPoseProperty;
import me.m56738.easyarmorstands.property.entity.EntityEquipmentProperty;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.property.entity.EntityScaleProperty;
import me.m56738.easyarmorstands.property.entity.EntityTagsProperty;
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
        registry.register(Property.of(EntityPropertyTypes.GLOWING, entity::isGlowing, entity::setGlowing));
        registry.register(new EntityLocationProperty(entity));
        registry.register(Property.of(EntityPropertyTypes.SILENT, entity::isSilent, entity::setSilent));
        registry.register(new EntityTagsProperty(entity));
        registry.register(Property.ofNullable(EntityPropertyTypes.CUSTOM_NAME, entity::customName, entity::customName));
        registry.register(Property.of(EntityPropertyTypes.CUSTOM_NAME_VISIBLE, entity::isCustomNameVisible, entity::setCustomNameVisible));
    }

    private void registerLivingEntityProperties(LivingEntity entity, PropertyRegistry registry) {
        EntityEquipment equipment = entity.getEquipment();
        if (equipment != null) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                registry.register(new EntityEquipmentProperty(equipment, slot));
            }
        }
        registry.register(new EntityScaleProperty(entity));
        registry.register(Property.of(EntityPropertyTypes.AI, entity::hasAI, entity::setAI));
    }

    private void registerArmorStandProperties(ArmorStand entity, PropertyRegistry registry) {
        registry.register(Property.of(ArmorStandPropertyTypes.ARMS, entity::hasArms, entity::setArms));
        registry.register(Property.of(ArmorStandPropertyTypes.BASE_PLATE, entity::hasBasePlate, entity::setBasePlate));
        registry.register(Property.of(ArmorStandPropertyTypes.MARKER, entity::isMarker, entity::setMarker));
        registry.register(Property.of(ArmorStandPropertyTypes.SIZE,
                () -> ArmorStandSize.get(entity),
                size -> entity.setSmall(size.isSmall())));
        registry.register(Property.of(EntityPropertyTypes.VISIBLE, entity::isVisible, entity::setVisible));
        registry.register(Property.of(ArmorStandPropertyTypes.CAN_TICK, entity::canTick, entity::setCanTick));
        registry.register(Property.of(ArmorStandPropertyTypes.GRAVITY, entity::hasGravity, entity::setGravity));
        registry.register(Property.of(ArmorStandPropertyTypes.INVULNERABLE, entity::isInvulnerable, entity::setInvulnerable));
        registry.register(new ArmorStandLockProperty(entity));
        for (ArmorStandPart part : ArmorStandPart.values()) {
            registry.register(new ArmorStandPoseProperty(entity, part));
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    private void registerMannequinProperties(Mannequin entity, PropertyRegistry registry) {
        registry.register(Property.of(MannequinPropertyTypes.MAIN_HAND, entity::getMainHand, entity::setMainHand));
        registry.register(Property.of(MannequinPropertyTypes.PROFILE, entity::getProfile, entity::setProfile));
        registry.register(Property.of(MannequinPropertyTypes.IMMOVABLE, entity::isImmovable, entity::setImmovable));
        registry.register(Property.ofNullable(MannequinPropertyTypes.DESCRIPTION, entity::getDescription, entity::setDescription));
        registry.register(new MannequinCapeVisibleProperty(entity));
        registry.register(new MannequinJacketVisibleProperty(entity));
        registry.register(new MannequinLeftSleeveVisibleProperty(entity));
        registry.register(new MannequinRightSleeveVisibleProperty(entity));
        registry.register(new MannequinLeftPantsVisibleProperty(entity));
        registry.register(new MannequinRightPantsVisibleProperty(entity));
        registry.register(new MannequinHatVisibleProperty(entity));
    }
}
