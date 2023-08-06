package me.m56738.easyarmorstands.property;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

@Deprecated
public interface EntityPropertyType<T> {
    @Nullable Property<T> bind(Entity entity);

    public @Nullable String getPermission();

    public Component getDisplayName();

    public Component getValueComponent(T value);
}
