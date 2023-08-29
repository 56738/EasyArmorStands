package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.MoveAxis;
import me.m56738.easyarmorstands.api.editor.button.MoveButton;
import me.m56738.easyarmorstands.api.editor.button.MoveButtonBuilder;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

public class MoveButtonBuilderImpl implements MoveButtonBuilder {
    private final Session session;
    private MoveAxis moveAxis;
    private double length = 3;
    private Component name;
    private ParticleColor color;

    public MoveButtonBuilderImpl(Session session) {
        this.session = session;
    }

    @Override
    public @NotNull MoveButtonBuilder setAxis(@NotNull MoveAxis moveAxis) {
        this.moveAxis = moveAxis;
        return this;
    }

    @Override
    public @NotNull MoveButtonBuilder setLength(double length) {
        this.length = length;
        return this;
    }

    @Override
    public @NotNull MoveButtonBuilder setName(@NotNull Component name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull MoveButtonBuilder setColor(@NotNull ParticleColor color) {
        this.color = color;
        return this;
    }

    @Override
    public @NotNull MoveButton build() {
        if (moveAxis == null) {
            throw new IllegalStateException("Axis not set");
        }
        Axis axis = moveAxis.getAxis();
        Component name = this.name;
        if (name == null) {
            TextColor textColor = TextColor.color(axis.getColor());
            name = Component.text(axis.getName(), textColor);
        }
        ParticleColor color = this.color;
        if (color == null) {
            color = axis.getColor();
        }
        return new MoveButtonImpl(session, moveAxis, length, name, color);
    }
}
