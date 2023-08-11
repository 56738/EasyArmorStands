package me.m56738.easyarmorstands.property;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PropertyContainer {
    static PropertyContainer empty() {
        return EmptyPropertyContainer.INSTANCE;
    }

    static PropertyContainer asPlayer(PropertyContainer container, Player player) {
        return new PlayerPropertyContainer(container, player);
    }

    <T> @Nullable Property<T> getOrNull(PropertyType<T> type);

    default <T> @NotNull Property<T> get(PropertyType<T> type) {
        Property<T> property = getOrNull(type);
        if (property == null) {
            throw new UnknownPropertyException(type);
        }
        return property;
    }

    boolean isValid();
}
