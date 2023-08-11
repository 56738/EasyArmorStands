package me.m56738.easyarmorstands.property;

import org.bukkit.entity.Player;

class PlayerPropertyContainer extends PropertyWrapperContainer {
    private final Player player;

    public PlayerPropertyContainer(PropertyContainer container, Player player) {
        super(container);
        this.player = player;
    }

    @Override
    protected <T> Property<T> wrap(Property<T> property) {
        return new PlayerPropertyWrapper<>(property, player);
    }
}
