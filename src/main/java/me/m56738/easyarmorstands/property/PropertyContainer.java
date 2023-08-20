package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.element.Element;
import me.m56738.easyarmorstands.property.type.PropertyType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface PropertyContainer {
    static PropertyContainer empty() {
        return EmptyPropertyContainer.INSTANCE;
    }

    static PropertyContainer immutable(PropertyContainer container) {
        if (container instanceof ImmutablePropertyContainer) {
            return container;
        }
        return new ImmutablePropertyContainer(container);
    }

    /**
     * Creates a property container which collects changes into a single history action.
     * <p>
     * Changes are performed immediately, but the history action is only created when {@link #commit()} is called.
     * Also performs permission checks.
     *
     * @param context The context which is performing the changes.
     * @param element The element whose properties should be used.
     * @return A property container which authorizes and tracks changes.
     * @see #identified(ChangeContext, PropertyContainer) Performing changes without adding them to the history
     */
    static PropertyContainer tracked(ChangeContext context, Element element) {
        return new TrackedPropertyContainer(element, context);
    }

    /**
     * Creates a property container which performs permission checks before modifying the value.
     *
     * @param context   The context which is performing the changes.
     * @param container The container whose properties should be used.
     * @return A property container which checks the permission of the property being modified.
     * @see #tracked(ChangeContext, Element) Tracking changes and adding them to the history
     */
    static PropertyContainer identified(ChangeContext context, PropertyContainer container) {
        return new PermissionCheckedPropertyContainer(container, context.permissions());
    }

    void forEach(Consumer<Property<?>> consumer);

    <T> @Nullable Property<T> getOrNull(PropertyType<T> type);

    default <T> @NotNull Property<T> get(PropertyType<T> type) {
        Property<T> property = getOrNull(type);
        if (property == null) {
            throw new UnknownPropertyException(type);
        }
        return property;
    }

    boolean isValid();

    void commit();
}
