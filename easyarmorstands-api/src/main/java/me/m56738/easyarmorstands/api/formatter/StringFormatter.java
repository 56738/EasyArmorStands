package me.m56738.easyarmorstands.api.formatter;

import net.kyori.adventure.text.Component;

public class StringFormatter<T> implements ValueFormatter<T> {
    @Override
    public Component format(T value) {
        return Component.text(formatAsString(value));
    }

    @Override
    public String formatAsString(T value) {
        return String.valueOf(value);
    }
}
