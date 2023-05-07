package me.m56738.easyarmorstands.property;

import cloud.commandframework.arguments.CommandArgument;
import me.m56738.easyarmorstands.command.EasCommandSender;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EntityProperty<E extends Entity, T> {
    T getValue(E entity);

    void setValue(E entity, T value);

    @NotNull String getName();

    @NotNull Class<E> getEntityType();

    @Nullable CommandArgument<EasCommandSender, T> getArgument();

    @NotNull Component getDisplayName();

    @NotNull Component getValueName(T value);

    @Nullable String getPermission();

    default boolean isSupported(E entity) {
        return true;
    }
}
