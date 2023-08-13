package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.property.ButtonPropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import org.bukkit.inventory.ItemStack;

public class ButtonPropertySlot<T> implements MenuSlot {
    private final Property<T> property;
    private final ButtonPropertyType<T> propertyType;
    private final PropertyContainer container;

    public ButtonPropertySlot(Property<T> property, ButtonPropertyType<T> propertyType, PropertyContainer container) {
        this.property = property;
        this.propertyType = propertyType;
        this.container = container;
    }

    @Override
    public ItemStack getItem() {
        return propertyType.createItem(property, container);
    }

    @Override
    public void onClick(MenuClick click) {
        if (propertyType.onClick(property, container, click)) {
            container.commit();
            click.updateItem();
        }
    }
}
