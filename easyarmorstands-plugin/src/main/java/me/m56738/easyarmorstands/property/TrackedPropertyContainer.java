package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyWrapperContainer;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.history.ChangeTracker;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A property container which collects changes into a single history action.
 * <p>
 * Changes are performed immediately, but the history action is only created when {@link #commit()} is called.
 * Also performs permission checks.
 */
public class TrackedPropertyContainer extends PropertyWrapperContainer {
    private final Element element;
    private final ChangeTracker tracker;

    public TrackedPropertyContainer(Element element, ChangeContext context) {
        super(new PermissionCheckedPropertyContainer(element, context));
        this.element = element;
        this.tracker = context.tracker();
    }

    @Override
    protected @NotNull <T> Property<T> wrap(@NotNull Property<T> property) {
        return new TrackedPropertyWrapper<>(tracker, element, property);
    }

    @Override
    public void commit(@Nullable Component description) {
        tracker.commit(description);
        super.commit(description);
    }
}
