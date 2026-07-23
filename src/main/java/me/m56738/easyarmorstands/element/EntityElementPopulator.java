package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.SkinPart;
import me.m56738.easyarmorstands.api.element.ConfigurableEntityElement;
import me.m56738.easyarmorstands.api.element.ElementTypeRegistry;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.InteractionPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.ItemDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.ItemFramePropertyTypes;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.menu.button.SpawnButton;
import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.entity.ArmorStand;
import me.m56738.easyarmorstands.platform.entity.BlockDisplay;
import me.m56738.easyarmorstands.platform.entity.Display;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Interaction;
import me.m56738.easyarmorstands.platform.entity.ItemDisplay;
import me.m56738.easyarmorstands.platform.entity.ItemFrame;
import me.m56738.easyarmorstands.platform.entity.LivingEntity;
import me.m56738.easyarmorstands.platform.entity.Mannequin;
import me.m56738.easyarmorstands.platform.entity.Mob;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.entity.TextDisplay;
import me.m56738.easyarmorstands.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandLockProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandPoseProperty;
import me.m56738.easyarmorstands.property.display.DisplayLeftRotationProperty;
import me.m56738.easyarmorstands.property.display.DisplayRightRotationProperty;
import me.m56738.easyarmorstands.property.display.DisplayScaleProperty;
import me.m56738.easyarmorstands.property.display.DisplayTranslationProperty;
import me.m56738.easyarmorstands.property.display.text.TextDisplayBackgroundProperty;
import me.m56738.easyarmorstands.property.entity.EntityComponentProperty;
import me.m56738.easyarmorstands.property.entity.EntityEquipmentProperty;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.property.entity.EntityOptionalComponentProperty;
import me.m56738.easyarmorstands.property.entity.EntityScaleProperty;
import me.m56738.easyarmorstands.property.entity.EntityTagsProperty;
import me.m56738.easyarmorstands.registry.EntityTypeKeys;
import me.m56738.easyarmorstands.registry.ItemTypeKeys;

import java.util.EnumSet;
import java.util.Objects;

public class EntityElementPopulator {
    private final Platform platform;

    private static final EnumSet<EquipmentSlot> DEFAULT_SLOTS = EnumSet.of(
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET,
            EquipmentSlot.HAND,
            EquipmentSlot.OFF_HAND);

    public EntityElementPopulator(Platform platform) {
        this.platform = platform;
    }

    public void populateSpawnMenu(EasyArmorStandsCommon eas, MenuBuilder builder, Player player) {
        ElementTypeRegistry elementTypeRegistry = eas.elementTypeRegistry();
        builder.addButton(new SpawnButton(eas, player, elementTypeRegistry.get(EntityTypeKeys.ARMOR_STAND), MenuIcon.of(Objects.requireNonNull(platform.getItemType(ItemTypeKeys.ARMOR_STAND)))));
        builder.addButton(new SpawnButton(eas, player, elementTypeRegistry.get(EntityTypeKeys.ITEM_DISPLAY), MenuIcon.of(Objects.requireNonNull(platform.getItemType(ItemTypeKeys.STICK)))));
        builder.addButton(new SpawnButton(eas, player, elementTypeRegistry.get(EntityTypeKeys.BLOCK_DISPLAY), MenuIcon.of(Objects.requireNonNull(platform.getItemType(ItemTypeKeys.STONE)))));
        builder.addButton(new SpawnButton(eas, player, elementTypeRegistry.get(EntityTypeKeys.TEXT_DISPLAY), MenuIcon.of(Objects.requireNonNull(platform.getItemType(ItemTypeKeys.NAME_TAG)))));
        builder.addButton(new SpawnButton(eas, player, elementTypeRegistry.get(EntityTypeKeys.INTERACTION), MenuIcon.of(Objects.requireNonNull(platform.getItemType(ItemTypeKeys.TARGET)))));
        builder.addButton(new SpawnButton(eas, player, elementTypeRegistry.get(EntityTypeKeys.MANNEQUIN), MenuIcon.of(Objects.requireNonNull(platform.getItemType(ItemTypeKeys.PLAYER_HEAD)))));
    }

    public void registerProperties(ConfigurableEntityElement<?> element) {
        Entity entity = element.getEntity();
        PropertyRegistry registry = element.getProperties();
        registerEntityProperties(entity, registry);
        if (entity instanceof LivingEntity) {
            registerLivingEntityProperties((LivingEntity) entity, registry);
        }
        if (entity instanceof Mob) {
            registerMobProperties((Mob) entity, registry);
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
        if (entity instanceof ItemFrame itemFrame) {
            registerItemFrameProperties(itemFrame, registry);
        }
    }

    private void registerEntityProperties(Entity entity, PropertyRegistry registry) {
        registry.register(Property.of(EntityPropertyTypes.GLOWING, entity::isGlowing, entity::setGlowing));
        registry.register(new EntityLocationProperty(entity));
        registry.register(Property.of(EntityPropertyTypes.SILENT, entity::isSilent, entity::setSilent));
        registry.register(new EntityTagsProperty(entity));
        registry.register(new EntityOptionalComponentProperty(EntityPropertyTypes.CUSTOM_NAME, entity, entity::getCustomName, entity::setCustomName));
        registry.register(Property.of(EntityPropertyTypes.CUSTOM_NAME_VISIBLE, entity::isCustomNameVisible, entity::setCustomNameVisible));
    }

    private void registerLivingEntityProperties(LivingEntity entity, PropertyRegistry registry) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (hasSlot(entity, slot)) {
                registry.register(new EntityEquipmentProperty(entity, slot));
            }
        }
        registry.register(new EntityScaleProperty(entity));
    }

    private void registerMobProperties(Mob entity, PropertyRegistry registry) {
        registry.register(Property.of(EntityPropertyTypes.AI, entity::hasAI, entity::setAI));
    }

    private boolean hasSlot(LivingEntity entity, EquipmentSlot slot) {
        if (entity.type().key().equals(EntityTypeKeys.ARMOR_STAND)) {
            return DEFAULT_SLOTS.contains(slot);
        }
        return entity.hasEquipmentSlot(slot);
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

    private void registerMannequinProperties(Mannequin entity, PropertyRegistry registry) {
        registry.register(Property.of(MannequinPropertyTypes.MAIN_HAND, entity::getMainHand, entity::setMainHand));
        registry.register(Property.of(MannequinPropertyTypes.PROFILE, entity::getProfile, entity::setProfile));
        registry.register(Property.of(MannequinPropertyTypes.IMMOVABLE, entity::isImmovable, entity::setImmovable));
        registry.register(new EntityOptionalComponentProperty(MannequinPropertyTypes.DESCRIPTION, entity, entity::getDescription, entity::setDescription));
        registry.register(Property.of(MannequinPropertyTypes.POSE, entity::getPose, entity::setPose));
        registry.register(Property.of(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.CAPE), entity::isCapeVisible, entity::setCapeVisible));
        registry.register(Property.of(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.JACKET), entity::isJacketVisible, entity::setJacketVisible));
        registry.register(Property.of(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.LEFT_SLEEVE), entity::isLeftSleeveVisible, entity::setLeftSleeveVisible));
        registry.register(Property.of(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.RIGHT_SLEEVE), entity::isRightSleeveVisible, entity::setRightSleeveVisible));
        registry.register(Property.of(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.LEFT_PANTS), entity::isLeftPantsVisible, entity::setLeftPantsVisible));
        registry.register(Property.of(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.RIGHT_PANTS), entity::isRightPantsVisible, entity::setRightPantsVisible));
        registry.register(Property.of(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.HAT), entity::isHatVisible, entity::setHatVisible));
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
        registry.register(Property.of(ItemDisplayPropertyTypes.ITEM, entity::getItemStack, entity::setItemStack));
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
        registry.register(new EntityComponentProperty(TextDisplayPropertyTypes.TEXT, entity, entity::getText, entity::setText));
    }

    private void registerInteractionProperties(Interaction entity, PropertyRegistry registry) {
        registry.register(Property.of(DisplayPropertyTypes.BOX_WIDTH, entity::getInteractionWidth, entity::setInteractionWidth));
        registry.register(Property.of(DisplayPropertyTypes.BOX_HEIGHT, entity::getInteractionHeight, entity::setInteractionHeight));
        registry.register(Property.of(InteractionPropertyTypes.RESPONSIVE, entity::isResponsive, entity::setResponsive));
    }

    private void registerItemFrameProperties(ItemFrame entity, PropertyRegistry registry) {
        registry.register(Property.of(ItemFramePropertyTypes.ITEM, entity::getItem, entity::setItem));
        registry.register(Property.of(ItemFramePropertyTypes.FIXED, entity::isFixed, entity::setFixed));
        registry.register(Property.of(EntityPropertyTypes.VISIBLE, entity::isVisible, entity::setVisible));
    }
}
