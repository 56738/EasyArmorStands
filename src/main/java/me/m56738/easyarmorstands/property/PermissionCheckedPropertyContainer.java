package me.m56738.easyarmorstands.property;

import net.kyori.adventure.permission.PermissionChecker;

class PermissionCheckedPropertyContainer extends PropertyWrapperContainer {
    private final PermissionChecker permissionChecker;

    public PermissionCheckedPropertyContainer(PropertyContainer container, PermissionChecker permissionChecker) {
        super(container);
        this.permissionChecker = permissionChecker;
    }

    @Override
    protected <T> Property<T> wrap(Property<T> property) {
        return new PermissionCheckedPropertyWrapper<>(property, permissionChecker);
    }
}
