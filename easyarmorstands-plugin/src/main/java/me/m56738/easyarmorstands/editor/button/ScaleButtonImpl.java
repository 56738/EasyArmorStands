package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.ScaleAxis;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.editor.button.ScaleButton;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.node.ScaleNode;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.function.Consumer;

public class ScaleButtonImpl implements ScaleButton, NodeFactoryButton {
    private final Session session;
    private final ScaleAxis scaleAxis;
    private final Vector3d position = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private final Vector3d direction = new Vector3d();
    private final Vector3d negativeEnd = new Vector3d();
    private final Vector3d positiveEnd = new Vector3d();
    private final LineParticle particle;
    private final double length;
    private final Component name;
    private final ParticleColor color;
    private Axis axis;

    public ScaleButtonImpl(Session session, ScaleAxis scaleAxis, double length, Component name, ParticleColor color) {
        this.name = name;
        this.session = session;
        this.scaleAxis = scaleAxis;
        this.length = length;
        this.particle = session.particleProvider().createLine();
        this.particle.setLength(length);
        this.color = color;
    }

    @Override
    public void update() {
        position.set(scaleAxis.getPosition());
        rotation.set(scaleAxis.getRotation());
        axis = scaleAxis.getAxis();
        axis.getDirection().rotate(rotation, direction);
        position.fma(-length / 2, direction, negativeEnd);
        position.fma(length / 2, direction, positiveEnd);
    }

    @Override
    public void intersect(EyeRay ray, Consumer<ButtonResult> results) {
        Vector3dc intersection = ray.intersectLine(negativeEnd, positiveEnd);
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
    public Component getName() {
        return name;
    }

    @Override
    public @NotNull Node createNode() {
        return new ScaleNode(session, scaleAxis, length, color, name);
    }
}
