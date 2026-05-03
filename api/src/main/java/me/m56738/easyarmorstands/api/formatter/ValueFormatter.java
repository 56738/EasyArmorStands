package me.m56738.easyarmorstands.api.formatter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface ValueFormatter<T> {
    Component format(T value);

    default String formatAsString(T value) {
        return PlainTextComponentSerializer.plainText().serialize(format(value));
    }
}
