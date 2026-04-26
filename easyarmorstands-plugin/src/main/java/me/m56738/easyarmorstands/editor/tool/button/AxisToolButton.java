package me.m56738.easyarmorstands.editor.tool.button;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.editor.tool.AxisTool;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Util;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.function.Consumer;

public class AxisToolButton implements Button {
    private final Session session;
    private final AxisTool<?> tool;
    private final Vector3d position = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private final Vector3d direction = new Vector3d();
    private final Vector3d negativeEnd = new Vector3d();
    private final Vector3d positiveEnd = new Vector3d();
    private final LineParticle particle;
    private final double length;
    private final ParticleColor color;
    private double scale;
    private Axis axis;

    public AxisToolButton(Session session, AxisTool<?> tool, double length, ParticleColor color) {
        this.session = session;
        this.tool = tool;
        this.length = length;
        this.particle = session.particleProvider().createLine();
        this.color = color;
        this.axis = tool.getAxis();
    }

    @Override
    public void update() {
        position.set(tool.getPosition());
        rotation.set(tool.getRotation());
        axis = tool.getAxis();
        axis.getDirection().rotate(rotation, direction);
        scale = session.getScale(position);
        position.fma(-length / 2 * scale, direction, negativeEnd);
        position.fma(length / 2 * scale, direction, positiveEnd);
    }

    @Override
    public void intersect(EyeRay ray, Consumer<ButtonResult> results) {
        Vector3dc intersection = ray.intersectLine(negativeEnd, positiveEnd, scale);
        if (intersection != null) {
            results.accept(ButtonResult.of(intersection));
        }
    }

    @Override
    public void updatePreview(boolean focused, boolean selected) {
        particle.setCenter(position);
        particle.setRotation(rotation);
        particle.setAxis(axis);
        particle.setColor(Button.color(focused, selected, color));
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

    public Session getSession() {
        return session;
    }

    public Vector3dc getPosition() {
        return position;
    }

    public Quaterniondc getRotation() {
        return rotation;
    }

    public Axis getAxis() {
        return axis;
    }

    public ParticleColor getColor() {
        return color;
    }

    public double getLength() {
        return length;
    }
}
