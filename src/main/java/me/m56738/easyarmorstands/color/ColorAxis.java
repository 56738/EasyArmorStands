package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public enum ColorAxis {
    RED(Message.component("easyarmorstands.color.red"), Color::getRed, Color::setRed, Color.RED, NamedTextColor.RED, DyeColor.RED, MenuIcon.of(Material.RED_CONCRETE)),
    GREEN(Message.component("easyarmorstands.color.green"), Color::getGreen, Color::setGreen, Color.LIME, NamedTextColor.GREEN, DyeColor.LIME, MenuIcon.of(Material.LIME_CONCRETE)),
    BLUE(Message.component("easyarmorstands.color.blue"), Color::getBlue, Color::setBlue, Color.BLUE, NamedTextColor.BLUE, DyeColor.BLUE, MenuIcon.of(Material.BLUE_CONCRETE));

    private final Component displayName;
    private final Getter getter;
    private final Setter setter;
    private final Color color;
    private final TextColor textColor;
    private final DyeColor dyeColor;
    private final MenuIcon icon;

    ColorAxis(Component displayName, Getter getter, Setter setter, Color color, TextColor textColor, DyeColor dyeColor, MenuIcon icon) {
        this.displayName = displayName.color(textColor);
        this.getter = getter;
        this.setter = setter;
        this.color = color;
        this.textColor = textColor;
        this.dyeColor = dyeColor;
        this.icon = icon;
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

    public TextColor getTextColor() {
        return textColor;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }

    public MenuIcon getIcon() {
        return icon;
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
