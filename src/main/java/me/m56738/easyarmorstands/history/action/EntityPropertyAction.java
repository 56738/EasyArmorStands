package me.m56738.easyarmorstands.history.action;

import me.m56738.easyarmorstands.property.Property;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;

public class EntityPropertyAction<E extends Entity, T> extends EntityAction<E> {
    private final EntityProperty<E, T> property;
    private final T oldValue;
    private final T newValue;
    private final Component description;

    public EntityPropertyAction(E entity, EntityProperty<E, T> property, T oldValue, T newValue, Component description) {
        super(entity);
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.description = description;
    }

    @Override
    public boolean execute() {
        return tryChange(newValue);
    }

    @Override
    public boolean undo() {
        return tryChange(oldValue);
    }

    private boolean tryChange(T value) {
        E entity = findEntity();
        Property<T> bound = property.bind(entity);
        if (bound == null) {
            throw new IllegalStateException("Unable to bind property");
        }
        bound.setValue(value);
        return true;
    }

    @Override
    public Component describe() {
        return description;
    }

    public interface EntityProperty<E extends Entity, T> {
        Property<T> bind(E entity);
    }
}
