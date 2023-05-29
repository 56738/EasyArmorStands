package me.m56738.easyarmorstands.property;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.context.CommandContext;
import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.command.sender.EasCommandSender;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EntityProperty<E extends Entity, T> {
    T getValue(E entity);

    TypeToken<T> getValueType();

    void setValue(E entity, T value);

    @NotNull String getName();

    @NotNull Class<E> getEntityType();

    ArgumentParser<EasCommandSender, T> getArgumentParser();

    default boolean hasDefaultValue() {
        return false;
    }

    default @Nullable T getDefaultValue(@NotNull CommandContext<EasCommandSender> ctx) {
        return null;
    }

    @NotNull Component getDisplayName();

    @NotNull Component getValueName(T value);

    @Nullable String getPermission();

    default boolean isCreativeModeRequired() {
        return false;
    }

    default boolean isSupported(E entity) {
        return true;
    }

    default boolean performChange(Session session, E entity, T value) {
        return session.setProperty(entity, this, value);
    }
}
