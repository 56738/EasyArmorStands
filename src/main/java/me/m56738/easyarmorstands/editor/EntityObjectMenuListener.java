package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.event.EntityObjectMenuInitializeEvent;
import me.m56738.easyarmorstands.menu.ColorPickerSlot;
import me.m56738.easyarmorstands.menu.DestroySlot;
import me.m56738.easyarmorstands.menu.builder.MenuBuilder;
import me.m56738.easyarmorstands.menu.slot.ButtonPropertySlot;
import me.m56738.easyarmorstands.property.ButtonPropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.property.entity.EntityEquipmentProperty;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityObjectMenuListener implements Listener {
    @EventHandler
    public void onInitializeMenu(EntityObjectMenuInitializeEvent event) {
        Player player = event.getPlayer();
        EntityObject entityObject = event.getEntityObject();
        MenuBuilder builder = event.getMenuBuilder();
        PropertyContainer properties = event.getProperties();
        if (entityObject.hasItemSlots() && player.hasPermission("easyarmorstands.color")) {
            builder.addUtility(new ColorPickerSlot());
        }
        if (entityObject instanceof DestroyableObject && player.hasPermission("easyarmorstands.destroy")) {
            builder.addButton(new DestroySlot((DestroyableObject) entityObject));
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
