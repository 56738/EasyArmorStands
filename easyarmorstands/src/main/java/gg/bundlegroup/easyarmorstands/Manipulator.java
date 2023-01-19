package gg.bundlegroup.easyarmorstands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;

public abstract class Manipulator {
    private final Component component;
    private final RGBLike color;

    protected Manipulator(String name, RGBLike color) {
        this.component = Component.text(name, TextColor.color(color));
        this.color = color;
    }

    public abstract void start();

    public abstract void update();

    public final Component getComponent() {
        return component;
    }

    public final RGBLike getColor() {
        return color;
    }
}
