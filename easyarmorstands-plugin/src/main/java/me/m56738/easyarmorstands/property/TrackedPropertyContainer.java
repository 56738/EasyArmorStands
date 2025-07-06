package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyWrapperContainer;
import me.m56738.easyarmorstands.history.ChangeTracker;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * A property container which collects changes into a single history action.
 * <p>
 * Changes are performed immediately, but the history action is only created when {@link #commit()} is called.
 * Also performs permission checks.
 */
public class TrackedPropertyContainer extends PropertyWrapperContainer {
    private final Element element;
    private final ChangeTracker tracker;

    public TrackedPropertyContainer(Element element, ChangeTracker tracker, Player player) {
        super(new PermissionCheckedPropertyContainer(element, player));
        this.element = element;
        this.tracker = tracker;
    }

    @Override
    protected @NotNull <T> Property<T> wrap(@NotNull Property<T> property) {
        return new TrackedPropertyWrapper<>(tracker, element, property);
    }
}
