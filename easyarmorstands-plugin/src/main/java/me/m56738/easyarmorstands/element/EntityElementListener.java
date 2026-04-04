package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.element.ConfigurableEntityElement;
import me.m56738.easyarmorstands.api.event.element.EntityElementInitializeEvent;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.InteractionPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.ItemDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandLockProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandPoseProperty;
import me.m56738.easyarmorstands.property.display.DisplayLeftRotationProperty;
import me.m56738.easyarmorstands.property.display.DisplayRightRotationProperty;
import me.m56738.easyarmorstands.property.display.DisplayScaleProperty;
import me.m56738.easyarmorstands.property.display.DisplayTranslationProperty;
import me.m56738.easyarmorstands.property.display.item.ItemDisplayItemProperty;
import me.m56738.easyarmorstands.property.display.text.TextDisplayBackgroundProperty;
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
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mannequin;
import org.bukkit.entity.TextDisplay;
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
        if (entity instanceof Display) {
            registerDisplayProperties((Display) entity, registry);
        }
        if (entity instanceof ItemDisplay) {
            registerItemDisplayProperties((ItemDisplay) entity, registry);
        }
        if (entity instanceof BlockDisplay) {
            registerBlockDisplayProperties((BlockDisplay) entity, registry);
        }
        if (entity instanceof TextDisplay) {
            registerTextDisplayProperties((TextDisplay) entity, registry);
        }
        if (entity instanceof Interaction) {
            registerInteractionProperties((Interaction) entity, registry);
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
        registry.register(Property.of(MannequinPropertyTypes.POSE, entity::getPose, entity::setPose));
        registry.register(new MannequinCapeVisibleProperty(entity));
        registry.register(new MannequinJacketVisibleProperty(entity));
        registry.register(new MannequinLeftSleeveVisibleProperty(entity));
        registry.register(new MannequinRightSleeveVisibleProperty(entity));
        registry.register(new MannequinLeftPantsVisibleProperty(entity));
        registry.register(new MannequinRightPantsVisibleProperty(entity));
        registry.register(new MannequinHatVisibleProperty(entity));
    }

    private void registerDisplayProperties(Display entity, PropertyRegistry registry) {
        registry.register(new DisplayTranslationProperty(entity));
        registry.register(new DisplayLeftRotationProperty(entity));
        registry.register(new DisplayScaleProperty(entity));
        registry.register(new DisplayRightRotationProperty(entity));
        registry.register(Property.of(DisplayPropertyTypes.BILLBOARD, entity::getBillboard, entity::setBillboard));
        registry.register(Property.ofNullable(DisplayPropertyTypes.BRIGHTNESS, entity::getBrightness, entity::setBrightness));
        registry.register(Property.of(DisplayPropertyTypes.BOX_WIDTH, entity::getDisplayWidth, entity::setDisplayWidth));
        registry.register(Property.of(DisplayPropertyTypes.BOX_HEIGHT, entity::getDisplayHeight, entity::setDisplayHeight));
        registry.register(Property.ofNullable(DisplayPropertyTypes.GLOW_COLOR, entity::getGlowColorOverride, entity::setGlowColorOverride));
        registry.register(Property.of(DisplayPropertyTypes.VIEW_RANGE, entity::getViewRange, entity::setViewRange));
    }

    private void registerItemDisplayProperties(ItemDisplay entity, PropertyRegistry registry) {
        registry.register(new ItemDisplayItemProperty(entity));
        registry.register(Property.of(ItemDisplayPropertyTypes.TRANSFORM, entity::getItemDisplayTransform, entity::setItemDisplayTransform));
    }

    private void registerBlockDisplayProperties(BlockDisplay entity, PropertyRegistry registry) {
        registry.register(Property.of(BlockDisplayPropertyTypes.BLOCK, entity::getBlock, entity::setBlock));
    }

    private void registerTextDisplayProperties(TextDisplay entity, PropertyRegistry registry) {
        registry.register(Property.of(TextDisplayPropertyTypes.ALIGNMENT, entity::getAlignment, entity::setAlignment));
        registry.register(new TextDisplayBackgroundProperty(entity));
        registry.register(Property.of(TextDisplayPropertyTypes.LINE_WIDTH, entity::getLineWidth, entity::setLineWidth));
        registry.register(Property.of(TextDisplayPropertyTypes.SEE_THROUGH, entity::isSeeThrough, entity::setSeeThrough));
        registry.register(Property.of(TextDisplayPropertyTypes.SHADOW, entity::isShadowed, entity::setShadowed));
        registry.register(Property.of(TextDisplayPropertyTypes.TEXT, entity::text, entity::text));
    }

    private void registerInteractionProperties(Interaction entity, PropertyRegistry registry) {
        registry.register(Property.of(DisplayPropertyTypes.BOX_WIDTH, entity::getInteractionWidth, entity::setInteractionWidth));
        registry.register(Property.of(DisplayPropertyTypes.BOX_HEIGHT, entity::getInteractionHeight, entity::setInteractionHeight));
        registry.register(Property.of(InteractionPropertyTypes.RESPONSIVE, entity::isResponsive, entity::setResponsive));
    }
}
