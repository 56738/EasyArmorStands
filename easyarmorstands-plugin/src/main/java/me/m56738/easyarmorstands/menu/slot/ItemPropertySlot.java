package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class ItemPropertySlot implements MenuSlot {
    private final Property<ItemStack> property;
    private final PropertyContainer container;

    public ItemPropertySlot(Property<ItemStack> property, PropertyContainer container) {
        this.property = property;
        this.container = container;
    }

    public Property<ItemStack> getProperty() {
        return property;
    }

    public PropertyContainer getContainer() {
        return container;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return property.getValue();
    }

    @Override
    public void onClick(MenuClick click) {
        String permission = property.getType().getPermission();
        if (permission != null && !click.player().hasPermission(permission)) {
            return;
        }
        click.allow();
        click.queueTask(item -> {
            if (item == null) {
                item = new ItemStack(Material.AIR);
            }
            property.setValue(item);
            container.commit();
        });
    }
}
