package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.SkinPart;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.EntityElement;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.event.menu.ElementMenuOpenEvent;
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
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.editor.display.node.DisplayBoxNode;
import me.m56738.easyarmorstands.menu.button.DestroyButton;
import me.m56738.easyarmorstands.menu.button.DisplayBoxButton;
import me.m56738.easyarmorstands.menu.button.MenuSlotButton;
import me.m56738.easyarmorstands.menu.slot.ColorPickerSlot;
import me.m56738.easyarmorstands.menu.slot.EntityCopyButton;
import me.m56738.easyarmorstands.menu.slot.EquipmentPropertySlot;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import me.m56738.easyarmorstands.property.button.BlockDataHandler;
import me.m56738.easyarmorstands.property.button.BooleanHandler;
import me.m56738.easyarmorstands.property.button.ButtonHandler;
import me.m56738.easyarmorstands.property.button.ComponentHandler;
import me.m56738.easyarmorstands.property.button.EnumHandler;
import me.m56738.easyarmorstands.property.button.GravityHandler;
import me.m56738.easyarmorstands.property.button.OptionalComponentHandler;
import me.m56738.easyarmorstands.property.button.PropertyButtonBuilder;
import me.m56738.easyarmorstands.property.button.ResolvableProfileHandler;
import me.m56738.easyarmorstands.property.button.TextBackgroundHandler;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Mannequin;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;

import java.util.function.Function;

public class ElementMenuListener implements Listener {
    @EventHandler
    public void onElementMenuOpen(ElementMenuOpenEvent event) {
        Element element = event.getElement();
        MenuBuilder builder = event.getBuilder();
        Player player = event.getPlayer();
        TrackedPropertyContainer container = new TrackedPropertyContainer(element, new EasPlayer(player));
        if (element instanceof DestroyableElement destroyableElement) {
            builder.addButton(new DestroyButton(destroyableElement));
        }
        MenuPopulator populator = new MenuPopulator(builder, container);
        populator.addButton(ArmorStandPropertyTypes.ARMS, Material.STICK);
        populator.addButton(ArmorStandPropertyTypes.BASE_PLATE, Material.STONE_SLAB);
        populator.addButton(ArmorStandPropertyTypes.GRAVITY, property -> {
            Property<Boolean> canTickProperty = container.getOrNull(ArmorStandPropertyTypes.CAN_TICK);
            ButtonHandler handler = canTickProperty != null
                    ? new GravityHandler(property, canTickProperty)
                    : new BooleanHandler(property);
            return new PropertyButtonBuilder<>(property)
                    .icon(MenuIcon.of(Material.FEATHER))
                    .handler(handler);
        });
        populator.addButton(ArmorStandPropertyTypes.INVULNERABLE, Material.GOLDEN_APPLE);
        populator.addButton(ArmorStandPropertyTypes.LOCK, Material.IRON_BARS, Component.translatable("easyarmorstands.property.armor-stand.lock.description"));
        populator.addButton(ArmorStandPropertyTypes.MARKER, Material.SUNFLOWER, Component.translatable("easyarmorstands.property.armor-stand.marker.description"));
        populator.addButton(ArmorStandPropertyTypes.SIZE, EnumHandler.provider(ArmorStandSize.class), Material.BONE_MEAL);
        populator.addButton(BlockDisplayPropertyTypes.BLOCK, BlockDataHandler::new, Material.OAK_STAIRS, Component.translatable("easyarmorstands.property.block-display.block.pick"));
        populator.addButton(DisplayPropertyTypes.BILLBOARD, EnumHandler.provider(Display.Billboard.class), Material.IRON_BARS, Component.translatable("easyarmorstands.property.display.billboard.description"));
        populator.addButton(EntityPropertyTypes.AI, Material.REDSTONE_BLOCK, Component.translatable("easyarmorstands.property.ai.description"));
        populator.addButton(EntityPropertyTypes.CUSTOM_NAME, OptionalComponentHandler.provider("/eas name"), Material.NAME_TAG);
        populator.addButton(EntityPropertyTypes.GLOWING, Material.GLOWSTONE_DUST, Component.translatable("easyarmorstands.property.glow.description"));
        populator.addButton(EntityPropertyTypes.SILENT, Material.NOTE_BLOCK, Component.translatable("easyarmorstands.property.silent.description"));
        populator.addButton(EntityPropertyTypes.VISIBLE, Material.POTION);
        populator.addButton(InteractionPropertyTypes.RESPONSIVE, Material.OAK_PRESSURE_PLATE, Component.translatable("easyarmorstands.property.interaction.responsive.description"));
        populator.addButton(ItemDisplayPropertyTypes.TRANSFORM, EnumHandler.provider(ItemDisplay.ItemDisplayTransform.class), Material.STICK, Component.translatable("easyarmorstands.property.item-display.transform.description"));
        populator.addButton(MannequinPropertyTypes.DESCRIPTION, OptionalComponentHandler.provider("/eas description"), Material.NAME_TAG, Component.translatable("easyarmorstands.property.mannequin.description.description"));
        populator.addButton(MannequinPropertyTypes.IMMOVABLE, Material.BEDROCK, Component.translatable("easyarmorstands.property.mannequin.immovable.description"));
        populator.addButton(MannequinPropertyTypes.MAIN_HAND, EnumHandler.provider(MainHand.class), Material.IRON_SWORD);
        populator.addButton(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.CAPE), Material.BLUE_BANNER);
        populator.addButton(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.HAT), Material.IRON_HELMET);
        populator.addButton(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.JACKET), Material.IRON_CHESTPLATE);
        populator.addButton(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.LEFT_PANTS), Material.LEVER);
        populator.addButton(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.LEFT_SLEEVE), Material.LEVER);
        populator.addButton(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.RIGHT_PANTS), Material.LEVER);
        populator.addButton(MannequinPropertyTypes.SKIN_PART_VISIBLE.get(SkinPart.RIGHT_SLEEVE), Material.LEVER);
        populator.addButton(MannequinPropertyTypes.POSE, EnumHandler.provider(Mannequin.validPoses()), Material.ARMOR_STAND);
        populator.addButton(MannequinPropertyTypes.PROFILE, ResolvableProfileHandler::new, Material.PLAYER_HEAD);
        populator.addButton(TextDisplayPropertyTypes.ALIGNMENT, EnumHandler.provider(TextDisplay.TextAlignment.class), Material.FEATHER);
        populator.addButton(TextDisplayPropertyTypes.BACKGROUND, TextBackgroundHandler::new, Material.STONE_SLAB);
        populator.addButton(TextDisplayPropertyTypes.SEE_THROUGH, Material.GLOWSTONE_DUST);
        populator.addButton(TextDisplayPropertyTypes.SHADOW, Material.STONE);
        populator.addButton(TextDisplayPropertyTypes.TEXT, ComponentHandler.provider("/eas text"), Material.NAME_TAG);

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            Property<ItemStack> property = container.getOrNull(EntityPropertyTypes.EQUIPMENT.get(slot));
            if (property != null) {
                builder.addButton(MenuSlotButton.toButton(property.getType().key(), new EquipmentPropertySlot(property, slot)));
            }
        }

        if (element instanceof EntityElement<?> entityElement && player.hasPermission(Permissions.COPY_ENTITY)) {
            Entity entity = entityElement.getEntity();
            ItemStack item = EntityCopyButton.createSpawnEgg(entity);
            if (item != null) {
                builder.addButton(new EntityCopyButton(entity, element.getType(), item));
            }
        }

        if (container.getOrNull(DisplayPropertyTypes.BOX_WIDTH) != null) {
            Session session = EasyArmorStands.get().sessionManager().getSession(player);
            if (session != null) {
                builder.addButton(new DisplayBoxButton(session, new DisplayBoxNode(session, container)));
            }
        }

        if (element instanceof MenuElement menuElement && player.hasPermission(Permissions.COLOR)) {
            builder.addButton(MenuSlotButton.toButton(ColorPickerSlot.KEY, new ColorPickerSlot(menuElement), MenuButtonCategory.HEADER));
        }
    }

    private record MenuPopulator(MenuBuilder builder, PropertyContainer container) {
        private <T> void addButton(PropertyType<T> type, Function<Property<T>, PropertyButtonBuilder<T>> provider) {
            Property<T> property = container.getOrNull(type);
            if (property != null) {
                builder.addButton(provider.apply(property).build());
            }
        }

        private void addButton(PropertyType<Boolean> type, Material material, Component... description) {
            addButton(type, BooleanHandler::new, material, description);
        }

        private <T> void addButton(PropertyType<T> type, Function<Property<T>, ? extends ButtonHandler> handlerProvider, Material material, Component... description) {
            addButton(type, handlerProvider, MenuIcon.of(material), description);
        }

        private <T> void addButton(PropertyType<T> type, Function<Property<T>, ? extends ButtonHandler> handlerProvider, MenuIcon icon, Component... description) {
            addButton(type, property -> new PropertyButtonBuilder<>(property)
                    .icon(icon)
                    .description(description)
                    .handler(handlerProvider.apply(property)));
        }
    }
}
