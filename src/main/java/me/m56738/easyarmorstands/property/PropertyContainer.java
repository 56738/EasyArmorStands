package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.element.Element;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public interface PropertyContainer {
    static PropertyContainer empty() {
        return EmptyPropertyContainer.INSTANCE;
    }

    static PropertyContainer immutable(PropertyContainer container) {
        return new ImmutablePropertyContainer(container);
    }

    /**
     * Creates a property container which collects changes into a single history action.
     * <p>
     * Changes are performed immediately, but the history action is only created when {@link #commit()} is called.
     * Also performs permission checks.
     *
     * @param element The element whose properties should be used.
     * @param player  The player who is performing the changes.
     * @return A property container which authorizes and tracks changes.
     * @see #identified(PropertyContainer, Player) Performing changes without adding them to the history
     */
    static PropertyContainer tracked(Element element, Player player) {
        return new TrackedPropertyContainer(element, player);
    }

    /**
     * Creates a property container which performs permission checks before modifying the value.
     *
     * @param container The container whose properties should be used.
     * @param player    The player who is performing the changes.
     * @return A property container which checks the permission of the property being modified.
     * @see #tracked(Element, Player) Tracking changes and adding them to the history
     */
    static PropertyContainer identified(PropertyContainer container, Player player) {
        return new PlayerPropertyContainer(container, player);
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
