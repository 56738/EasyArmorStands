package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.RotateAxis;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.editor.button.RotateButton;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.particle.CircleParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.node.RotateNode;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.function.Consumer;

public class RotateButtonImpl implements RotateButton, NodeFactoryButton {
    private final Session session;
    private final RotateAxis rotateAxis;
    private final Vector3d position = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private final Vector3d direction = new Vector3d();
    private final CircleParticle particle;
    private final double radius;
    private final double length;
    private final Component name;
    private final ParticleColor color;
    private Axis axis;

    public RotateButtonImpl(Session session, RotateAxis rotateAxis, double radius, double length, Component name, ParticleColor color) {
        this.session = session;
        this.rotateAxis = rotateAxis;
        this.particle = session.particleProvider().createCircle();
        this.particle.setRadius(radius);
        this.radius = radius;
        this.length = length;
        this.name = name;
        this.color = color;
    }

    @Override
    public void update() {
        position.set(rotateAxis.getPosition());
        rotation.set(rotateAxis.getRotation());
        axis = rotateAxis.getAxis();
        axis.getDirection().rotate(rotation, direction);
    }

    @Override
    public void intersect(@NotNull EyeRay ray, @NotNull Consumer<@NotNull ButtonResult> results) {
        Vector3dc intersection = ray.intersectCircle(position, direction, radius);
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
    }

    @Override
    public void showPreview() {
        session.addParticle(particle);
    }

    @Override
    public void hidePreview() {
        session.removeParticle(particle);
    }

    @Override
    public @NotNull Component getName() {
        return name;
    }

    @Override
    public @NotNull Node createNode() {
        return new RotateNode(session, rotateAxis, color, radius, length, name);
    }
}
