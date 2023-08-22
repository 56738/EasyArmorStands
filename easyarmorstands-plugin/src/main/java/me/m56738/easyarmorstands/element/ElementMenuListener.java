package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.event.menu.EntityElementMenuInitializeEvent;
import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.menu.slot.ColorPickerSlot;
import me.m56738.easyarmorstands.menu.slot.DestroySlot;
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
        properties.forEach(property -> handleProperty(property, properties, builder));
    }

    private <T> void handleProperty(Property<T> property, PropertyContainer container, MenuBuilder builder) {
        PropertyType<T> type = property.getType();
        type.populateMenu(builder, property, container);
    }
}
