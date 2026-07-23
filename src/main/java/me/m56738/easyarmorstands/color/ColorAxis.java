package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.platform.color.RGBColor;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.registry.ItemTypeKeys;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.util.RGBLike;

public enum ColorAxis {
    RED(Message.component("easyarmorstands.color.red").color(NamedTextColor.RED), RGBLike::red, RGBColor::withRed, RGBColor.RED, ItemTypeKeys.RED_CONCRETE),
    GREEN(Message.component("easyarmorstands.color.green").color(NamedTextColor.GREEN), RGBLike::green, RGBColor::withGreen, RGBColor.GREEN, ItemTypeKeys.LIME_CONCRETE),
    BLUE(Message.component("easyarmorstands.color.blue").color(NamedTextColor.BLUE), RGBLike::blue, RGBColor::withBlue, RGBColor.BLUE, ItemTypeKeys.BLUE_CONCRETE);

    private final Component displayName;
    private final Getter getter;
    private final Setter setter;
    private final RGBColor color;
    private final Key itemTypeKey;

    ColorAxis(Component displayName, Getter getter, Setter setter, RGBColor color, Key itemTypeKey) {
        this.displayName = displayName;
        this.getter = getter;
        this.setter = setter;
        this.color = color;
        this.itemTypeKey = itemTypeKey;
    }

    public Component getDisplayName() {
        return displayName;
    }

    public int get(RGBLike color) {
        return getter.get(color);
    }

    public RGBColor set(RGBColor color, int value) {
        return setter.set(color, value);
    }

    public RGBColor getColor() {
        return color;
    }

    public MenuIcon getIcon(Platform platform) {
        return MenuIcon.of(platform.getItemType(itemTypeKey));
    }

    @FunctionalInterface
    private interface Getter {
        int get(RGBLike color);
    }

    @FunctionalInterface
    private interface Setter {
        RGBColor set(RGBColor color, int value);
    }
}
