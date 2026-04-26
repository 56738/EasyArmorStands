package me.m56738.easyarmorstands.editor.tool.button;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.particle.CircleParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Util;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.function.Consumer;

public class AxisRotateButton implements Button {
    private final Session session;
    private final AxisRotateTool tool;
    private final Vector3d position = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private final Vector3d direction = new Vector3d();
    private final CircleParticle particle;
    private final double radius;
    private final ParticleColor color;
    private double scale;
    private Axis axis;

    public AxisRotateButton(Session session, AxisRotateTool tool, double radius, ParticleColor color) {
        this.session = session;
        this.tool = tool;
        this.particle = session.particleProvider().createCircle();
        this.radius = radius;
        this.color = color;
        this.axis = tool.getAxis();
    }

    @Override
    public void update() {
        position.set(tool.getPosition());
        rotation.set(tool.getRotation());
        scale = session.getScale(position);
        axis = tool.getAxis();
        axis.getDirection().rotate(rotation, direction);
    }

    @Override
    public void intersect(EyeRay ray, Consumer<ButtonResult> results) {
        Vector3dc intersection = ray.intersectCircle(position, direction, radius * scale, scale);
        if (intersection != null) {
            results.accept(ButtonResult.of(intersection));
        }
    }

    @Override
    public void updatePreview(boolean focused, boolean selected) {
        particle.setCenter(position);
        particle.setRotation(rotation);
        particle.setAxis(axis);
        particle.setColor(focused ? ParticleColor.YELLOW : color);
        particle.setRadius(radius * scale);
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
}
