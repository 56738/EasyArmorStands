package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.capability.itemcolor.ItemColorCapability;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ColorPickerContext {
    private final Property<ItemStack> property;
    private final PropertyContainer container;
    private final ItemColorCapability itemColorCapability;

    public ColorPickerContext(Property<ItemStack> property, PropertyContainer container) {
        this.property = property;
        this.container = container;
        this.itemColorCapability = EasyArmorStands.getInstance().getCapability(ItemColorCapability.class);
    }

    public ItemStack getItem() {
        return property.getValue();
    }

    public Color getColor() {
        ItemStack item = property.getValue();
        if (item == null) {
            return null;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return null;
        }
        return itemColorCapability.getColor(meta);
    }

    public void setColor(Color color, Menu menu) {
        ItemStack item = property.getValue().clone();
        if (item == null) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        if (itemColorCapability.setColor(meta, color)) {
            item.setItemMeta(meta);
            property.setValue(item);
            container.commit();
            menu.updateItems(slot -> slot instanceof ColorSlot);
        }
    }
}
