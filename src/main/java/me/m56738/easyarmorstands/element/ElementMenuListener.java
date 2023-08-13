package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.event.EntityElementMenuInitializeEvent;
import me.m56738.easyarmorstands.menu.builder.MenuBuilder;
import me.m56738.easyarmorstands.menu.slot.ButtonPropertySlot;
import me.m56738.easyarmorstands.menu.slot.ColorPickerSlot;
import me.m56738.easyarmorstands.menu.slot.DestroySlot;
import me.m56738.easyarmorstands.property.ButtonPropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.property.entity.EntityEquipmentProperty;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ElementMenuListener implements Listener {
    @EventHandler
    public void onInitializeMenu(EntityElementMenuInitializeEvent event) {
        Player player = event.getPlayer();
        MenuElement element = event.getElement();
        MenuBuilder builder = event.getMenuBuilder();
        PropertyContainer properties = event.getProperties();
        if (element.hasItemSlots() && player.hasPermission("easyarmorstands.color")) {
            builder.addUtility(new ColorPickerSlot(element));
        }
        if (element instanceof DestroyableElement && player.hasPermission("easyarmorstands.destroy")) {
            builder.addButton(new DestroySlot((DestroyableElement) element));
        }
        EntityEquipmentProperty.populate(builder, properties);
        properties.forEach(property -> handleProperty(property, properties, builder));
    }

    private <T> void handleProperty(Property<T> property, PropertyContainer container, MenuBuilder builder) {
        PropertyType<T> type = property.getType();
        if (type instanceof ButtonPropertyType) {
            ButtonPropertySlot<T> slot = new ButtonPropertySlot<>(property, (ButtonPropertyType<T>) type, container);
            builder.addButton(slot);
        }
    }
}
