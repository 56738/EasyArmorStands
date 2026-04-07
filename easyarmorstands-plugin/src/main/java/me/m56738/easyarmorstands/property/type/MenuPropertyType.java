package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.PropertyRegistry;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.menu.button.MenuSlotButton;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface MenuPropertyType<T> extends PropertyType<T> {
    @Deprecated
    default @Nullable MenuSlot createSlot(Property<T> property, PropertyContainer container) {
        return null;
    }

    @Deprecated
    default @Nullable MenuButton createButton(Property<T> property) {
        MenuSlot slot = createSlot(property, new PropertyRegistry() {
            @Override
            public void commit() {
                property.commit();
            }

            @Override
            public boolean isValid() {
                return false;
            }
        });
        if (slot == null) {
            return null;
        }
        return MenuSlotButton.toButton(slot);
    }

    default void addToMenu(MenuBuilder builder, Property<T> property) {
        MenuButton button = createButton(property);
        if (button != null) {
            builder.addButton(button);
        }
    }
}
