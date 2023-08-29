package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.RotateAxis;
import me.m56738.easyarmorstands.api.editor.button.RotateButton;
import me.m56738.easyarmorstands.api.editor.button.RotateButtonBuilder;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

public class RotateButtonBuilderImpl implements RotateButtonBuilder {
    private final Session session;
    private RotateAxis rotateAxis;
    private double radius = 1;
    private double length = 3;
    private ParticleColor color;
    private Component name;

    public RotateButtonBuilderImpl(Session session) {
        this.session = session;
    }

    @Override
    public @NotNull RotateButtonBuilder setAxis(RotateAxis axis) {
        this.rotateAxis = axis;
        return this;
    }

    @Override
    public @NotNull RotateButtonBuilder setLength(double length) {
        this.length = length;
        return this;
    }

    @Override
    public @NotNull RotateButtonBuilder setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    @Override
    public @NotNull RotateButtonBuilder setName(@NotNull Component name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull RotateButtonBuilder setColor(@NotNull ParticleColor color) {
        this.color = color;
        return this;
    }

    @Override
    public @NotNull RotateButton build() {
        if (rotateAxis == null) {
            throw new IllegalStateException("Axis not set");
        }
        Axis axis = rotateAxis.getAxis();
        Component name = this.name;
        if (name == null) {
            TextColor textColor = TextColor.color(axis.getColor());
            name = Message.component(
                    "easyarmorstands.node.rotate-around-axis",
                    Component.text(axis.getName())
            ).color(textColor);
        }
        ParticleColor color = this.color;
        if (color == null) {
            color = axis.getColor();
        }
        return new RotateButtonImpl(session, rotateAxis, radius, length, name, color);
    }
}
