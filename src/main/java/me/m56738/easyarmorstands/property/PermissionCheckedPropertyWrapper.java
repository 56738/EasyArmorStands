package me.m56738.easyarmorstands.property;

import net.kyori.adventure.permission.PermissionChecker;
import org.jetbrains.annotations.Nullable;

class PermissionCheckedPropertyWrapper<T> implements Property<T> {
    private final Property<T> property;
    private final PermissionChecker permissionChecker;
    private Boolean hasPermissionCache;

    PermissionCheckedPropertyWrapper(Property<T> property, PermissionChecker permissionChecker) {
        this.property = property;
        this.permissionChecker = permissionChecker;
    }

    public boolean hasPermission() {
        if (hasPermissionCache != null) {
            return hasPermissionCache;
        }

        String permission = property.getType().getPermission();
        boolean result;
        if (permission != null) {
            result = permissionChecker.test(permission);
        } else {
            result = true;
        }
        hasPermissionCache = result;
        return result;
    }

    @Override
    public PropertyType<T> getType() {
        return property.getType();
    }

    @Override
    public T getValue() {
        return property.getValue();
    }

    @Override
    public boolean setValue(T value) {
        if (!hasPermission()) {
            return false;
        }
        return property.setValue(value);
    }

    @Override
    public @Nullable PendingChange prepareChange(T value) {
        if (!hasPermission()) {
            return null;
        }
        if (property.prepareChange(value) == null) {
            return null;
        }
        return PendingChange.of(this, value);
    }
}
