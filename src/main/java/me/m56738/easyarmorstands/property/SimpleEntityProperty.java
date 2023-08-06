package me.m56738.easyarmorstands.property;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

@Deprecated
public abstract class SimpleEntityProperty<T> implements Property<T> {
    private final EntityPropertyType<T> type;
    private final Entity entity;

    protected SimpleEntityProperty(EntityPropertyType<T> type, Entity entity) {
        this.type = type;
        this.entity = entity;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
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
}
