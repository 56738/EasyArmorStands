package me.m56738.easyarmorstands.common.editor.button;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.editor.tool.AxisTool;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.common.util.Util;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.function.Consumer;

public abstract class AxisToolButton implements NodeFactoryButton {
    private final Session session;
    private final AxisTool<?> tool;
    private final Vector3d position = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private final Vector3d direction = new Vector3d();
    private final Vector3d negativeEnd = new Vector3d();
    private final Vector3d positiveEnd = new Vector3d();
    private final LineParticle particle;
    private final double length;
    private final Component name;
    private final ParticleColor color;
    private double scale;
    private Axis axis;

    public AxisToolButton(Session session, AxisTool<?> tool, double length, Component name, ParticleColor color) {
        this.name = name;
        this.session = session;
        this.tool = tool;
        this.length = length;
        this.particle = session.particleProvider().createLine();
        this.color = color;
    }

    @Override
    public void update() {
        position.set(tool.position());
        rotation.set(tool.getRotation());
        axis = tool.getAxis();
        axis.getDirection().rotate(rotation, direction);
        scale = session.getScale(position);
        position.fma(-length / 2 * scale, direction, negativeEnd);
        position.fma(length / 2 * scale, direction, positiveEnd);
    }

    @Override
    public void intersect(@NotNull EyeRay ray, @NotNull Consumer<@NotNull ButtonResult> results) {
        Vector3dc intersection = ray.intersectLine(negativeEnd, positiveEnd, scale);
        if (intersection != null) {
            results.accept(ButtonResult.of(intersection));
        }
    }

    @Override
    public void updatePreview(boolean focused) {
        particle.setCenter(position);
        particle.setRotation(rotation);
        particle.setAxis(axis);
        particle.setColor(focused ? ParticleColor.YELLOW : color);
        particle.setLength(length * scale);
        particle.setWidth(Util.LINE_WIDTH * scale);
    }

    @Override
    public void showPreview() {
        session.addParticle(particle);
    }

    @Override
    public void hidePreview() {
        session.removeParticle(particle);
    }

    public @NotNull Session getSession() {
        return session;
    }

    @Override
    public @NotNull Component getName() {
        return name;
    }

    public @NotNull Vector3dc getPosition() {
        return position;
    }

    public @NotNull Quaterniondc getRotation() {
        return rotation;
    }

    public @NotNull Axis getAxis() {
        return axis;
    }

    public @NotNull ParticleColor getColor() {
        return color;
    }

    public double getLength() {
        return length;
    }
}
