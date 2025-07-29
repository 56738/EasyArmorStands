package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.util.Color;
import me.m56738.easyarmorstands.common.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public enum ColorAxis {
    RED(Message.component("easyarmorstands.color.red"), Color::red, Color::withRed, Color.RED, NamedTextColor.RED),
    GREEN(Message.component("easyarmorstands.color.green"), Color::green, Color::withGreen, Color.GREEN, NamedTextColor.GREEN),
    BLUE(Message.component("easyarmorstands.color.blue"), Color::blue, Color::withBlue, Color.BLUE, NamedTextColor.BLUE);

    private final Component displayName;
    private final Getter getter;
    private final Setter setter;
    private final Color color;
    private final TextColor textColor;

    ColorAxis(Component displayName, Getter getter, Setter setter, Color color, TextColor textColor) {
        this.displayName = displayName.color(textColor);
        this.getter = getter;
        this.setter = setter;
        this.color = color;
        this.textColor = textColor;
    }

    public Component getDisplayName() {
        return displayName;
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

    @FunctionalInterface
    private interface Getter {
        int get(Color color);
    }

    @FunctionalInterface
    private interface Setter {
        Color set(Color color, int value);
    }
}
