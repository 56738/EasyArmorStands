package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.AxisRotateButton;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.particle.CircleParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.node.AxisRotateToolNode;
import me.m56738.easyarmorstands.lib.joml.Quaterniond;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class AxisRotateButtonImpl implements NodeFactoryButton, AxisRotateButton {
    private final Session session;
    private final AxisRotateTool tool;
    private final Vector3d position = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private final Vector3d direction = new Vector3d();
    private final CircleParticle particle;
    private final double radius;
    private final double length;
    private final Component name;
    private final ParticleColor color;
    private double scale;
    private Axis axis;

    public AxisRotateButtonImpl(Session session, AxisRotateTool tool, double radius, double length, Component name, ParticleColor color) {
        this.session = session;
        this.tool = tool;
        this.particle = session.particleProvider().createCircle();
        this.radius = radius;
        this.length = length;
        this.name = name;
        this.color = color;
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
    public void intersect(@NotNull EyeRay ray, @NotNull Consumer<@NotNull ButtonResult> results) {
        Vector3dc intersection = ray.intersectCircle(position, direction, radius * scale, scale);
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

    @Override
    public @NotNull Component getName() {
        return name;
    }

    @Override
    public @NotNull Node createNode() {
        return new AxisRotateToolNode(session, tool.start(), radius, length, name, color, position, rotation, axis);
    }
}
