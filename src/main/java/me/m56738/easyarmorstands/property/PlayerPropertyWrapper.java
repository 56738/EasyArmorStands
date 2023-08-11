package me.m56738.easyarmorstands.property;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

class PlayerPropertyWrapper<T> implements Property<T> {
    private final Property<T> property;
    private final Player player;
    private Boolean hasPermissionCache;

    PlayerPropertyWrapper(Property<T> property, Player player) {
        this.property = property;
        this.player = player;
    }

    public boolean hasPermission() {
        if (hasPermissionCache != null) {
            return hasPermissionCache;
        }

        String permission = property.getType().getPermission();
        boolean result;
        if (permission != null) {
            result = player.hasPermission(permission);
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
