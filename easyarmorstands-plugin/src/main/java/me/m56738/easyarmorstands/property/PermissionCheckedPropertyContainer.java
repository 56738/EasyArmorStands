package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyWrapperContainer;
import me.m56738.easyarmorstands.context.ChangeContext;

/**
 * A property container which performs permission checks before modifying the value.
 */
public class PermissionCheckedPropertyContainer extends PropertyWrapperContainer {
    private final Element element;
    private final ChangeContext context;

    public PermissionCheckedPropertyContainer(Element element, ChangeContext context) {
        super(element.getProperties());
        this.element = element;
        this.context = context;
    }

    @Override
    protected <T> Property<T> wrap(Property<T> property) {
        return new PermissionCheckedPropertyWrapper<>(property, element, context);
    }
}
