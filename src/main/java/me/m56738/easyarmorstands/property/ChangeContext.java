package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.event.PlayerChangePropertyEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Objects;

public interface ChangeContext {
    Player getPlayer();

    <T> void applyChange(PropertyChange<T> change);

    default <T> boolean canChange(PropertyChange<T> change) {
        T value = change.getValue();
        T oldValue = change.getProperty().getValue();
        if (Objects.equals(oldValue, value)) {
            return true;
        }

        Player player = getPlayer();
        if (!player.hasPermission(change.getProperty().getType().getPermission())) {
            return false;
        }

        PlayerChangePropertyEvent event = new PlayerChangePropertyEvent(player, change);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    default <T> boolean tryChange(Property<T> property, T value) {
        return tryChange(new PropertyChange<>(property, value));
    }

    default <T> boolean tryChange(PropertyChange<T> change) {
        if (!canChange(change)) {
            return false;
        }
        applyChange(change);
        return true;
    }

    default boolean tryChange(Collection<PropertyChange<?>> changes) {
        for (PropertyChange<?> change : changes) {
            if (!canChange(change)) {
                return false;
            }
        }
        for (PropertyChange<?> change : changes) {
            applyChange(change);
        }
        return true;
    }
}
