package gg.bundlegroup.easyarmorstands.common.manipulator;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;

public abstract class AbstractManipulator implements Manipulator {
    private final Component component;
    private final RGBLike color;

    protected AbstractManipulator(String name, RGBLike color) {
        this.component = Component.text(name, TextColor.color(color));
        this.color = color;
    }

    @Override
    public final Component component() {
        return component;
    }

    public final RGBLike color() {
        return color;
    }
}
