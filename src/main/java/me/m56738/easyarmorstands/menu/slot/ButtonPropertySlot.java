package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.property.ButtonPropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;

public class ButtonPropertySlot<T> implements MenuSlot {
    private final Property<T> property;
    private final ButtonPropertyType<T> propertyType;
    private final Session session;
    private final PropertyContainer container;

    public ButtonPropertySlot(Property<T> property, ButtonPropertyType<T> propertyType, Session session, PropertyContainer container) {
        this.property = property;
        this.propertyType = propertyType;
        this.session = session;
        this.container = container;
    }

    @Override
    public ItemStack getItem() {
        return propertyType.createItem(property, container);
    }

    @Override
    public void onClick(MenuClick click) {
        click.cancel();

        if (!click.isLeftClick()) {
            return;
        }

        String permission = propertyType.getPermission();
        if (permission != null && !session.getPlayer().hasPermission(permission)) {
            session.sendMessage(Component.text("You don't have permission to edit this property.", NamedTextColor.RED));
            return;
        }
        propertyType.onClick(property, container);
        session.commit();
        click.updateItem();
    }
}
