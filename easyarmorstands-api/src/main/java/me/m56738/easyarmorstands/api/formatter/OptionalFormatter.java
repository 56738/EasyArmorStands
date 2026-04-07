package me.m56738.easyarmorstands.api.formatter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.Optional;

public class OptionalFormatter<T> implements ValueFormatter<Optional<T>> {
    private final ValueFormatter<T> formatter;
    private final Component fallback;
    private final String fallbackString;

    public OptionalFormatter(ValueFormatter<T> formatter) {
        this(formatter, Component.translatable("easyarmorstands.property.common.none", NamedTextColor.GRAY, TextDecoration.ITALIC));
    }

    public OptionalFormatter(ValueFormatter<T> formatter, Component fallback) {
        this.formatter = formatter;
        this.fallback = fallback;
        this.fallbackString = PlainTextComponentSerializer.plainText().serialize(fallback);
    }

    @Override
    public Component format(Optional<T> value) {
        return value.map(formatter::format).orElse(fallback);
    }

    @Override
    public String formatAsString(Optional<T> value) {
        return value.map(formatter::formatAsString).orElse(fallbackString);
    }
}
