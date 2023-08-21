package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.event.menu.EntityElementMenuInitializeEvent;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.button.PropertyButton;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.PropertyTypes;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.menu.builder.SplitMenuBuilder;
import me.m56738.easyarmorstands.menu.slot.ButtonPropertySlot;
import me.m56738.easyarmorstands.menu.slot.ColorPickerSlot;
import me.m56738.easyarmorstands.menu.slot.DestroySlot;
import me.m56738.easyarmorstands.menu.slot.ItemPropertySlot;
import net.kyori.adventure.identity.Identity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class ElementMenuListener implements Listener {
    public static void populateEquipmentSlots(MenuBuilder builder, PropertyContainer container) {
        populateEquipmentSlot(builder, container, 19, EquipmentSlot.HEAD);
        populateEquipmentSlot(builder, container, 27, EasyArmorStands.getInstance().getCapability(EquipmentCapability.class).getOffHand());
        populateEquipmentSlot(builder, container, 28, EquipmentSlot.CHEST);
        populateEquipmentSlot(builder, container, 29, EquipmentSlot.HAND);
        populateEquipmentSlot(builder, container, 37, EquipmentSlot.LEGS);
        populateEquipmentSlot(builder, container, 46, EquipmentSlot.FEET);
    }

    private static void populateEquipmentSlot(MenuBuilder builder, PropertyContainer container, int index, EquipmentSlot equipmentSlot) {
        if (equipmentSlot == null) {
            return;
        }
        Property<ItemStack> property = container.getOrNull(PropertyTypes.ENTITY_EQUIPMENT.get(equipmentSlot));
        if (property == null) {
            return;
        }
        ItemPropertySlot slot = new ItemPropertySlot(property, container);
        if (builder instanceof SplitMenuBuilder) {
            ((SplitMenuBuilder) builder).setSlot(index, slot);
        } else {
            builder.addUtility(slot);
        }
    }

    @EventHandler
    public void onInitializeMenu(EntityElementMenuInitializeEvent event) {
        Player player = event.getPlayer();
        Locale locale = EasyArmorStands.getInstance().getAdventure().player(player)
                .getOrDefault(Identity.LOCALE, Locale.US);
        MenuElement element = event.getElement();
        MenuBuilder builder = event.getMenuBuilder();
        PropertyContainer properties = event.getProperties();
        if (element.hasItemSlots() && player.hasPermission("easyarmorstands.color")) {
            builder.addUtility(new ColorPickerSlot(element));
        }
        if (element instanceof DestroyableElement && player.hasPermission("easyarmorstands.destroy")) {
            builder.addButton(new DestroySlot((DestroyableElement) element));
        }
        populateEquipmentSlots(builder, properties);
        properties.forEach(property -> handleProperty(property, properties, builder, locale));
    }

    private <T> void handleProperty(Property<T> property, PropertyContainer container, MenuBuilder builder, Locale locale) {
        PropertyType<T> type = property.getType();
        PropertyButton button = type.createButton(property, container);
        if (button != null) {
            ButtonPropertySlot slot = new ButtonPropertySlot(button, locale);
            builder.addButton(slot);
        }
    }
}
