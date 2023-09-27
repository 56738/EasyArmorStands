package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.context.ChangeContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class PermissionCheckedPropertyWrapper<T> implements Property<T> {
    private final Property<T> property;
    private final Element element;
    private final ChangeContext context;
    private Boolean hasPermissionCache;

    PermissionCheckedPropertyWrapper(Property<T> property, Element element, ChangeContext context) {
        this.property = property;
        this.element = element;
        this.context = context;
    }

    private boolean hasPermission() {
        if (hasPermissionCache != null) {
            return hasPermissionCache;
        }

        String permission = property.getType().getPermission();
        boolean result;
        if (permission != null) {
            result = context.permissions().test(permission);
        } else {
            result = true;
        }
        hasPermissionCache = result;
        return result;
    }

    @Override
    public @NotNull PropertyType<T> getType() {
        return property.getType();
    }

    @Override
    public @NotNull T getValue() {
        return property.getValue();
    }

    private boolean isAllowed(T value) {
        if (!hasPermission()) {
            return false;
        }
        return context.canChangeProperty(element, property, value);
    }

    @Override
    public boolean setValue(@NotNull T value) {
        if (!isAllowed(value)) {
            return false;
        }
        return property.setValue(value);
    }

    @Override
    public @Nullable PendingChange prepareChange(@NotNull T value) {
        if (!isAllowed(value)) {
            return null;
        }
        return property.prepareChange(value);
    }
}
