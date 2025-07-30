package me.m56738.easyarmorstands.common.property;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyWrapperContainer;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import org.jetbrains.annotations.NotNull;

/**
 * A property container which performs permission checks before modifying the value.
 */
public class PermissionCheckedPropertyContainer extends PropertyWrapperContainer {
    private final CommonPlatform platform;
    private final Element element;
    private final Player player;

    public PermissionCheckedPropertyContainer(CommonPlatform platform, Element element, Player player) {
        super(element.getProperties());
        this.platform = platform;
        this.element = element;
        this.player = player;
    }

    @Override
    protected @NotNull <T> Property<T> wrap(@NotNull Property<T> property) {
        return new PermissionCheckedPropertyWrapper<>(platform, property, element, player);
    }
}
