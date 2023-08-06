package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.EntityPropertyAction;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

@Deprecated
public class EntityPropertyBinding<E extends Entity, T> implements Property<T> {
    private final EntityPropertyType<T> type;
    private final E entity;
    private final EntityPropertyAccessor<E, T> accessor;

    public EntityPropertyBinding(EntityPropertyType<T> type, E entity, EntityPropertyAccessor<E, T> accessor) {
        this.entity = entity;
        this.type = type;
        this.accessor = accessor;
    }

    @Override
    public T getValue() {
        return accessor.getValue(entity);
    }

    @Override
    public void setValue(T value) {
        accessor.setValue(entity, value);
    }

    @Override
    public Action createChangeAction(T oldValue, T value) {
        return new EntityPropertyAction<>(entity, type::bind, oldValue, value, Component.text("Changed ").append(getDisplayName()));
    }

    @Override
    public @Nullable String getPermission() {
        return type.getPermission();
    }

    @Override
    public Component getDisplayName() {
        return type.getDisplayName();
    }

    @Override
    public Component getValueComponent(T value) {
        return type.getValueComponent(value);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
