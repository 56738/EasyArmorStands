package me.m56738.easyarmorstands.api.formatter;

import net.kyori.adventure.text.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberFormatter<T extends Number> implements ValueFormatter<T> {
    private final NumberFormat format;

    public NumberFormatter(NumberFormat format) {
        this.format = format;
    }

    public static <T extends Number> NumberFormatter<T> pattern(String pattern) {
        return new NumberFormatter<>(new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(Locale.ROOT)));
    }

    @Override
    public Component format(T value) {
        return Component.text(formatAsString(value));
    }

    @Override
    public String formatAsString(T value) {
        return format.format(value);
    }
}
