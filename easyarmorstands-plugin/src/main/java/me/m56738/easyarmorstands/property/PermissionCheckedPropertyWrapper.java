package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class PermissionCheckedPropertyWrapper<T> implements Property<T> {
    private final Property<T> property;
    private final Element element;
    private final Player player;
    private Boolean hasPermissionCache;

    PermissionCheckedPropertyWrapper(Property<T> property, Element element, Player player) {
        this.property = property;
        this.element = element;
        this.player = player;
    }

    private boolean hasPermission() {
        if (hasPermissionCache != null) {
            return hasPermissionCache;
        }

        boolean result = property.getType().canChange(player);
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
        return EasyArmorStandsPlugin.getInstance().canChangeProperty(player, element, property, value);
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

    @Override
    public boolean canChange(@NotNull Player player) {
        return hasPermission();
    }

    @Override
    public boolean isValid() {
        return property.isValid();
    }
}
