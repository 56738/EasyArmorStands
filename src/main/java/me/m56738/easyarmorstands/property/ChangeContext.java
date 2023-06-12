package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.event.PlayerEditEntityPropertyEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Objects;

public interface ChangeContext {
    Player getPlayer();

    <E extends Entity, T> void applyChange(EntityPropertyChange<E, T> change);

    default <E extends Entity, T> boolean canChange(EntityPropertyChange<E, T> change) {
        E entity = change.getEntity();
        EntityProperty<E, T> property = change.getProperty();
        T value = change.getValue();
        T oldValue = property.getValue(entity);
        if (Objects.equals(oldValue, value)) {
            return true;
        }

        Player player = getPlayer();
        if (!player.hasPermission(property.getPermission())) {
            return false;
        }

        PlayerEditEntityPropertyEvent<E, T> event = new PlayerEditEntityPropertyEvent<>(player, entity, property, oldValue, value);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    default <E extends Entity, T> boolean tryChange(E entity, EntityProperty<E, T> property, T value) {
        return tryChange(new EntityPropertyChange<>(entity, property, value));
    }

    default <E extends Entity, T> boolean tryChange(EntityPropertyChange<E, T> change) {
        if (!canChange(change)) {
            return false;
        }
        applyChange(change);
        return true;
    }

    default boolean tryChange(Collection<EntityPropertyChange<?, ?>> changes) {
        for (EntityPropertyChange<?, ?> change : changes) {
            if (!canChange(change)) {
                return false;
            }
        }
        for (EntityPropertyChange<?, ?> change : changes) {
            applyChange(change);
        }
        return true;
    }
}
