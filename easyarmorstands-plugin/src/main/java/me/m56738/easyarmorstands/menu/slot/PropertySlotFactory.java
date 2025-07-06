package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PropertySlotFactory<T> implements MenuSlotFactory {
    private final PropertyType<T> type;

    public PropertySlotFactory(PropertyType<T> type) {
        this.type = type;
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context) {
        Element element = context.element();
        if (element == null) {
            return null;
        }
        Property<T> property = element.getProperties().getOrNull(type);
        if (property == null) {
            return null;
        }
        if (!type.canChange(context.player())) {
            return null;
        }
        return type.createSlot(element);
    }
}
