package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.property.ButtonPropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ButtonPropertySlot<T> implements MenuSlot {
    private final Property<T> property;
    private final ButtonPropertyType<T> propertyType;
    private final Session session;

    public ButtonPropertySlot(Property<T> property, ButtonPropertyType<T> propertyType, Session session) {
        this.property = property;
        this.propertyType = propertyType;
        this.session = session;
    }

    @Override
    public ItemStack getItem() {
        return propertyType.createItem(property, new SessionPropertyContainer(session));
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
        propertyType.onClick(property, new SessionPropertyContainer(session));
        session.commit();
        click.updateItem();
    }

    private static class SessionPropertyContainer implements PropertyContainer {
        // TODO move?
        private final Session session;

        private SessionPropertyContainer(Session session) {
            this.session = session;
        }

        @Override
        public @Nullable <T> Property<T> get(PropertyType<T> type) {
            return session.findProperty(type);
        }
    }
}
