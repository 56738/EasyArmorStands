package me.m56738.easyarmorstands.color;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;

public enum ColorAxis {
    RED(Color::getRed, Color::setRed, Color.RED, NamedTextColor.RED, DyeColor.RED),
    GREEN(Color::getGreen, Color::setGreen, Color.LIME, NamedTextColor.GREEN, DyeColor.LIME),
    BLUE(Color::getBlue, Color::setBlue, Color.BLUE, NamedTextColor.BLUE, DyeColor.BLUE);

    private final Getter getter;
    private final Setter setter;
    private final Color color;
    private final TextColor textColor;
    private final DyeColor dyeColor;

    ColorAxis(Getter getter, Setter setter, Color color, TextColor textColor, DyeColor dyeColor) {
        this.getter = getter;
        this.setter = setter;
        this.color = color;
        this.textColor = textColor;
        this.dyeColor = dyeColor;
    }

    public int get(Color color) {
        return getter.get(color);
    }

    public Color set(Color color, int value) {
        return setter.set(color, value);
    }

    public Color getColor() {
        return color;
    }

    public TextColor getTextColor() {
        return textColor;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }

    @FunctionalInterface
    private interface Getter {
        int get(Color color);
    }

    @FunctionalInterface
    private interface Setter {
        Color set(Color color, int value);
    }
}
