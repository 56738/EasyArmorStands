package me.m56738.easyarmorstands.property;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class PlayerPropertyContainer implements PropertyContainer {
    private final PropertyContainer container;
    private final Player player;

    public PlayerPropertyContainer(PropertyContainer container, Player player) {
        this.container = container;
        this.player = player;
    }

    private <T> Property<T> wrap(Property<T> property) {
        if (property == null) {
            return null;
        }
        return new PlayerPropertyWrapper<>(property, player);
    }

    @Override
    public @Nullable <T> Property<T> getOrNull(PropertyType<T> type) {
        return wrap(container.getOrNull(type));
    }

    @Override
    public @NotNull <T> Property<T> get(PropertyType<T> type) {
        return wrap(container.get(type));
    }

    @Override
    public boolean isValid() {
        return container.isValid();
    }
}
