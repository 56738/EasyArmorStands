package me.m56738.easyarmorstands.property;

import io.leangen.geantyref.TypeToken;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface EntityProperty<E extends Entity, T> {
    T getValue(E entity);

    TypeToken<T> getValueType();

    void setValue(E entity, T value);

    @NotNull String getName();

    @NotNull Class<E> getEntityType();

    @NotNull Component getDisplayName();

    @NotNull Component getValueName(T value);

    default @NotNull String getValueClipboardContent(T value) {
        return PlainTextComponentSerializer.plainText().serialize(getValueName(value));
    }

    @Nullable String getPermission();

    default boolean isSupported(E entity) {
        return true;
    }
}
