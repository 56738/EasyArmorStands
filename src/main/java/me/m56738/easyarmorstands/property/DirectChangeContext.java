package me.m56738.easyarmorstands.property;

import org.bukkit.entity.Player;

public class DirectChangeContext implements ChangeContext {
    private final Player player;

    public DirectChangeContext(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public <T> void applyChange(PropertyChange<T> change) {
        change.getProperty().setValue(change.getValue());
    }
}
