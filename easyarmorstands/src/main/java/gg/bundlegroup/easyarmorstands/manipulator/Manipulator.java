package gg.bundlegroup.easyarmorstands.manipulator;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;
import org.joml.Vector3dc;

public abstract class Manipulator {
    private final Component component;
    private final RGBLike color;

    protected Manipulator(String name, RGBLike color) {
        this.component = Component.text(name, TextColor.color(color));
        this.color = color;
    }

    public abstract void start(Vector3dc cursor);

    public abstract void update(boolean freeLook);

    public abstract Vector3dc getCursor();

    public final Component getComponent() {
        return component;
    }

    public final RGBLike getColor() {
        return color;
    }
}
