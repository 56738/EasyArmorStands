package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.SkinPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.menu.button.MenuButtonCategory;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.InteractionPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.ItemDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.ItemFramePropertyTypes;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.editor.display.layer.DisplayBoxLayer;
import me.m56738.easyarmorstands.menu.button.DestroyButton;
import me.m56738.easyarmorstands.menu.button.DisplayBoxButton;
import me.m56738.easyarmorstands.menu.button.MenuSlotButton;
import me.m56738.easyarmorstands.menu.slot.ColorPickerSlot;
import me.m56738.easyarmorstands.menu.slot.EntityCopyButton;
import me.m56738.easyarmorstands.menu.slot.EquipmentPropertySlot;
import me.m56738.easyarmorstands.menu.slot.ItemPropertySlot;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.platform.entity.Display;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.ItemDisplay;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.entity.TextDisplay;
import me.m56738.easyarmorstands.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.util.MainHand;
import me.m56738.easyarmorstands.property.button.BlockDataHandler;
import me.m56738.easyarmorstands.property.button.BooleanHandler;
import me.m56738.easyarmorstands.property.button.ButtonHandler;
import me.m56738.easyarmorstands.property.button.ComponentHandler;
import me.m56738.easyarmorstands.property.button.CustomNameHandler;
import me.m56738.easyarmorstands.property.button.EnumHandler;
import me.m56738.easyarmorstands.property.button.GravityHandler;
import me.m56738.easyarmorstands.property.button.OptionalComponentHandler;
import me.m56738.easyarmorstands.property.button.PropertyButtonBuilder;
import me.m56738.easyarmorstands.property.button.ResolvableProfileHandler;
import me.m56738.easyarmorstands.property.button.TextBackgroundHandler;
import me.m56738.easyarmorstands.registry.ItemTypeKeys;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;

import java.util.function.Function;

public class ElementMenuPopulator {
    private final EasyArmorStandsCommon eas;

    public ElementMenuPopulator(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    public void populateMenu(Element element, MenuBuilder builder, Player player, PropertyContainer container) {
        MenuPopulator populator = new MenuPopulator(eas, builder, container, player);
        populator.addButton(ArmorStandPropertyTypes.ARMS, ItemTypeKeys.STICK);
        populator.addButton(ArmorStandPropertyTypes.BASE_PLATE, ItemTypeKeys.STONE_SLAB);
        populator.addButton(ArmorStandPropertyTypes.GRAVITY, property -> {
            Property<Boolean> canTickProperty = container.getOrNull(ArmorStandPropertyTypes.CAN_TICK);
            ButtonHandler handler = canTickProperty != null
                    ? new GravityHandler(eas, property, canTickProperty)
                    : new BooleanHandler(eas, property);
            return new PropertyButtonBuilder<>(property)
                    .icon(MenuIcon.of(eas.platform().getItemType(ItemTypeKeys.FEATHER)))
                    .handler(handler);
        });
        populator.addButton(ArmorStandPropertyTypes.INVULNERABLE, ItemTypeKeys.GOLDEN_APPLE);
        populator.addButton(ArmorStandPropertyTypes.LOCK, ItemTypeKeys.IRON_BARS, Component.translatable("easyarmorstands.property.armor-stand.lock.description"));
        populator.addButton(ArmorStandPropertyTypes.MARKER, ItemTypeKeys.SUNFLOWER, Component.translatable("easyarmorstands.property.armor-stand.marker.description"));
        populator.addButton(ArmorStandPropertyTypes.SIZE, EnumHandler.provider(eas, ArmorStandSize.class), ItemTypeKeys.BONE_MEAL);
        populator.addButton(BlockDisplayPropertyTypes.BLOCK, BlockDataHandler.provider(eas), ItemTypeKeys.OAK_STAIRS, Component.translatable("easyarmorstands.property.block-display.block.pick"));
        populator.addButton(DisplayPropertyTypes.BILLBOARD, EnumHandler.provider(eas, Display.Billboard.class), ItemTypeKeys.IRON_BARS, Component.translatable("easyarmorstands.property.display.billboard.description"));
        populator.addButton(EntityPropertyTypes.AI, ItemTypeKeys.REDSTONE_BLOCK, Component.translatable("easyarmorstands.property.ai.description"));
        populator.addButton(EntityPropertyTypes.CUSTOM_NAME, property -> {
            Property<Boolean> customNameVisibleProperty = container.getOrNull(EntityPropertyTypes.CUSTOM_NAME_VISIBLE);
            ButtonHandler handler = customNameVisibleProperty != null
                    ? new CustomNameHandler(eas, property, customNameVisibleProperty)
                    : new OptionalComponentHandler(eas, property);
            return new PropertyButtonBuilder<>(property)
                    .icon(MenuIcon.of(eas.platform().getItemType(ItemTypeKeys.NAME_TAG)))
                    .handler(handler);
        });
        populator.addButton(EntityPropertyTypes.GLOWING, ItemTypeKeys.GLOWSTONE_DUST, Component.translatable("easyarmorstands.property.glow.description"));
        populator.addButton(EntityPropertyTypes.SILENT, ItemTypeKeys.NOTE_BLOCK, Component.translatable("easyarmorstands.property.silent.description"));
        populator.addButton(EntityPropertyTypes.VISIBLE, ItemTypeKeys.POTION);
        populator.addButton(InteractionPropertyTypes.RESPONSIVE, ItemTypeKeys.OAK_PRESSURE_PLATE, Component.translatable("easyarmorstands.property.interaction.responsive.description"));
        populator.addButton(ItemDisplayPropertyTypes.TRANSFORM, EnumHandler.provider(eas, ItemDisplay.ItemDisplayTransform.class), ItemTypeKeys.STICK, Component.translatable("easyarmorstands.property.item-display.transform.description"));
        populator.addButton(MannequinPropertyTypes.DESCRIPTION, OptionalComponentHandler.provider(eas), ItemTypeKeys.NAME_TAG, Component.translatable("easyarmorstands.property.mannequin.description.description"));
        populator.addButton(MannequinPropertyTypes.IMMOVABLE, ItemTypeKeys.BEDROCK, Component.translatable("easyarmorstands.property.mannequin.immovable.description"));
        populator.addButton(MannequinPropertyTypes.MAIN_HAND, EnumHandler.provider(eas, MainHand.class), ItemTypeKeys.IRON_SWORD);
        populator.addButton(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.CAPE), ItemTypeKeys.BLUE_BANNER);
        populator.addButton(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.HAT), ItemTypeKeys.IRON_HELMET);
        populator.addButton(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.JACKET), ItemTypeKeys.IRON_CHESTPLATE);
        populator.addButton(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.LEFT_PANTS), ItemTypeKeys.LEVER);
        populator.addButton(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.LEFT_SLEEVE), ItemTypeKeys.LEVER);
        populator.addButton(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.RIGHT_PANTS), ItemTypeKeys.LEVER);
        populator.addButton(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.RIGHT_SLEEVE), ItemTypeKeys.LEVER);
        populator.addButton(MannequinPropertyTypes.POSE, EnumHandler.provider(eas, eas.platform().getValidMannequinPoses()), ItemTypeKeys.ARMOR_STAND);
        populator.addButton(MannequinPropertyTypes.PROFILE, ResolvableProfileHandler.provider(eas), ItemTypeKeys.PLAYER_HEAD);
        populator.addButton(TextDisplayPropertyTypes.ALIGNMENT, EnumHandler.provider(eas, TextDisplay.TextAlignment.class), ItemTypeKeys.FEATHER);
        populator.addButton(TextDisplayPropertyTypes.BACKGROUND, TextBackgroundHandler.provider(eas), ItemTypeKeys.STONE_SLAB);
        populator.addButton(TextDisplayPropertyTypes.SEE_THROUGH, ItemTypeKeys.GLOWSTONE_DUST);
        populator.addButton(TextDisplayPropertyTypes.SHADOW, ItemTypeKeys.STONE);
        populator.addButton(TextDisplayPropertyTypes.TEXT, ComponentHandler.provider(eas), ItemTypeKeys.NAME_TAG);
        populator.addButton(ItemFramePropertyTypes.FIXED, ItemTypeKeys.BEDROCK);

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            Property<ItemStack> property = container.getOrNull(EntityPropertyTypes.EQUIPMENT.get(slot));
            if (property != null && property.canChange(player)) {
                builder.addButton(MenuSlotButton.toButton(property.getType().key(), new EquipmentPropertySlot(eas, property, slot)));
            }
        }

        Property<ItemStack> itemDisplayItemProperty = container.getOrNull(ItemDisplayPropertyTypes.ITEM);
        if (itemDisplayItemProperty != null && itemDisplayItemProperty.canChange(player)) {
            builder.addButton(MenuSlotButton.toButton(itemDisplayItemProperty.getType().key(),
                    new ItemPropertySlot(eas, itemDisplayItemProperty)));
        }

        Property<ItemStack> itemFrameItemProperty = container.getOrNull(ItemFramePropertyTypes.ITEM);
        if (itemFrameItemProperty != null && itemFrameItemProperty.canChange(player)) {
            builder.addButton(MenuSlotButton.toButton(itemFrameItemProperty.getType().key(),
                    new ItemPropertySlot(eas, itemFrameItemProperty)));
        }

        if (element instanceof EntityElement<?> entityElement && player.hasPermission(Permissions.COPY_ENTITY)) {
            Entity entity = entityElement.getEntity();
            ItemStack item = eas.createEntitySpawnEgg(entity);
            if (!item.isEmpty()) {
                builder.addButton(new EntityCopyButton(eas, entity, element.getType(), item));
            }
        }

        if (element instanceof DisplayElement<?>) {
            Session session = eas.sessionManager().getSession(player);
            if (session != null) {
                builder.addButton(new DisplayBoxButton(eas, session, new DisplayBoxLayer(session, container)));
            }
        }

        if (element instanceof DestroyableElement destroyableElement && player.hasPermission(Permissions.DESTROY)) {
            builder.addButton(new DestroyButton(eas, destroyableElement));
        }

        if (element instanceof MenuElement menuElement && player.hasPermission(Permissions.COLOR)) {
            builder.addButton(MenuSlotButton.toButton(ColorPickerSlot.KEY, new ColorPickerSlot(eas, menuElement), MenuButtonCategory.HEADER));
        }
    }

    private record MenuPopulator(EasyArmorStandsCommon eas, MenuBuilder builder, PropertyContainer container,
                                 Player player) {
        private <T> void addButton(PropertyType<T> type, Function<Property<T>, PropertyButtonBuilder<T>> provider) {
            Property<T> property = container.getOrNull(type);
            if (property != null && property.canChange(player)) {
                builder.addButton(provider.apply(property).build());
            }
        }

        private void addButton(PropertyType<Boolean> type, Key itemTypeKey, Component... description) {
            addButton(type, BooleanHandler.provider(eas), itemTypeKey, description);
        }

        private <T> void addButton(PropertyType<T> type, Function<Property<T>, ? extends ButtonHandler> handlerProvider, Key itemTypeKey, Component... description) {
            addButton(type, handlerProvider, MenuIcon.of(eas.platform().getItemType(itemTypeKey)), description);
        }

        private <T> void addButton(PropertyType<T> type, Function<Property<T>, ? extends ButtonHandler> handlerProvider, MenuIcon icon, Component... description) {
            addButton(type, property -> new PropertyButtonBuilder<>(property)
                    .icon(icon)
                    .description(description)
                    .handler(handlerProvider.apply(property)));
        }
    }
}
