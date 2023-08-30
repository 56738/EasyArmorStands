package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.ScaleAxis;
import me.m56738.easyarmorstands.api.editor.button.ScaleButton;
import me.m56738.easyarmorstands.api.editor.button.ScaleButtonBuilder;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

public class ScaleButtonBuilderImpl implements ScaleButtonBuilder {
    private final Session session;
    private ScaleAxis scaleAxis;
    private double length = 3;
    private Component name;
    private ParticleColor color;

    public ScaleButtonBuilderImpl(Session session) {
        this.session = session;
    }

    @Override
    public @NotNull ScaleButtonBuilder setAxis(@NotNull ScaleAxis scaleAxis) {
        this.scaleAxis = scaleAxis;
        return this;
    }

    @Override
    public @NotNull ScaleButtonBuilder setLength(double length) {
        this.length = length;
        return this;
    }

    @Override
    public @NotNull ScaleButtonBuilder setName(@NotNull Component name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull ScaleButtonBuilder setColor(@NotNull ParticleColor color) {
        this.color = color;
        return this;
    }

    @Override
    public @NotNull ScaleButton build() {
        if (scaleAxis == null) {
            throw new IllegalStateException("Axis not set");
        }
        Axis axis = scaleAxis.getAxis();
        Component name = this.name;
        if (name == null) {
            TextColor textColor = TextColor.color(axis.getColor());
            name = Message.component(
                    "easyarmorstands.node.scale-along-axis",
                    Component.text(axis.getName())
            ).color(textColor);
        }
        ParticleColor color = this.color;
        if (color == null) {
            color = axis.getColor();
        }
        return new ScaleButtonImpl(session, scaleAxis, length, name, color);
    }
}
