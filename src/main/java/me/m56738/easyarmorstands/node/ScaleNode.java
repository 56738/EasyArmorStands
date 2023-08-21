package me.m56738.easyarmorstands.node;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.DoubleArgument;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.bone.ScaleBone;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.particle.PointParticle;
import me.m56738.easyarmorstands.util.Cursor3D;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.function.Consumer;

public class ScaleNode extends EditNode implements NodeButton, ValueNode<Double> {
    private final Session session;
    private final ScaleBone bone;
    private final Component name;
    private final Axis axis;
    private final Vector3d direction;
    private final ParticleColor color;
    private final double length;
    private final Cursor3D cursor;
    private final Vector3d negativeEnd = new Vector3d();
    private final Vector3d positiveEnd = new Vector3d();
    private final Vector3d currentPosition = new Vector3d();
    private final Vector3d initialPosition = new Vector3d();
    private final Vector3d initialCursor = new Vector3d();
    private final Vector3d initialOffset = new Vector3d();
    private final Vector3d currentOffset = new Vector3d();
    private final LineParticle axisParticle;
    private final LineParticle cursorLineParticle;
    private final PointParticle positiveParticle;
    private final PointParticle negativeParticle;
    private double initialScale;
    private double currentLength;
    private Double manualValue;

    public ScaleNode(Session session, ScaleBone bone, Component name, Axis axis, ParticleColor color, double length) {
        super(session);
        this.session = session;
        this.bone = bone;
        this.name = name;
        this.axis = axis;
        this.direction = new Vector3d(axis.getDirection());
        this.color = color;
        this.length = length;
        this.cursor = new Cursor3D(session);
        this.axisParticle = session.particleFactory().createLine();
        this.cursorLineParticle = session.particleFactory().createLine();
        this.cursorLineParticle.setColor(ParticleColor.WHITE);
        this.positiveParticle = session.particleFactory().createPoint();
        this.positiveParticle.setBillboard(false);
        this.positiveParticle.setSize(Util.PIXEL);
        this.negativeParticle = session.particleFactory().createPoint();
        this.negativeParticle.setBillboard(false);
        this.negativeParticle.setSize(Util.PIXEL);
    }

    @Override
    public void onEnter(EnterContext context) {
        manualValue = null;
        bone.getRotation().transform(axis.getDirection(), direction).normalize();
        initialPosition.set(bone.getOrigin());
        initialScale = bone.getScale(axis);
        initialCursor.set(context.cursorOrDefault(initialPosition));
        cursor.start(initialCursor);
        currentLength = length;

        updateAxisParticle();
        updateCursorLineParticle();
        session.addParticle(axisParticle);
        session.addParticle(cursorLineParticle);
    }

    @Override
    protected void abort() {
        bone.setScale(axis, initialScale);
    }

    @Override
    public void onUpdate(UpdateContext context) {
        cursor.update(false);

        initialCursor.sub(initialPosition, initialOffset);
        cursor.get().sub(initialPosition, currentOffset);
        double t;
        double scale;
        if (manualValue != null) {
            scale = manualValue;
            t = scale / initialScale * initialOffset.dot(direction);
        } else {
            t = currentOffset.dot(direction);
            scale = t / initialOffset.dot(direction) * initialScale;
        }
        bone.setScale(axis, scale);

        currentLength = Math.max(Math.abs(t), length);
        initialPosition.fma(t, direction, currentPosition);

        updateAxisParticle();
        updateCursorLineParticle();

        session.setActionBar(Component.text()
                .append(name)
                .append(Component.text(": "))
                .append(Component.text(Util.SCALE_FORMAT.format(scale))));
    }

    @Override
    public void onExit(ExitContext context) {
        session.removeParticle(axisParticle);
        session.removeParticle(cursorLineParticle);
        cursor.stop();
        bone.commit();
    }

    private void updateAxisParticle() {
        axisParticle.setAxis(axis);
        axisParticle.setCenter(initialPosition);
        axisParticle.setRotation(bone.getRotation());
        axisParticle.setLength(currentLength * 2);
        axisParticle.setColor(color);
    }

    private void updateCursorLineParticle() {
        cursorLineParticle.setFromTo(currentPosition, cursor.get());
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public Node createNode() {
        return this;
    }

    @Override
    public Component getValueComponent(Double value) {
        return Component.text(Util.SCALE_FORMAT.format(value), TextColor.color(color));
    }

    @Override
    public ArgumentParser<CommandSender, Double> getParser() {
        return new DoubleArgument.DoubleParser<>(0, Double.POSITIVE_INFINITY);
    }

    @Override
    public void setValue(Double value) {
        manualValue = value;
    }

    @Override
    public void update() {
        bone.getRotation().transform(axis.getDirection(), direction).normalize();
        Vector3dc position = bone.getOrigin();
        position.fma(-length, direction, negativeEnd);
        position.fma(length, direction, positiveEnd);
    }

    @Override
    public void intersect(EyeRay ray, Consumer<ButtonResult> results) {
        Vector3dc positive = ray.intersectPoint(positiveEnd);
        if (positive != null) {
            results.accept(ButtonResult.of(positive, 1));
        }
        Vector3dc negative = ray.intersectPoint(negativeEnd);
        if (negative != null) {
            results.accept(ButtonResult.of(negative, 1));
        }
    }

    @Override
    public void updatePreview(boolean focused) {
        positiveParticle.setPosition(positiveEnd);
        negativeParticle.setPosition(negativeEnd);
        positiveParticle.setRotation(bone.getRotation());
        negativeParticle.setRotation(bone.getRotation());
        positiveParticle.setColor(focused ? ParticleColor.YELLOW : color);
        negativeParticle.setColor(focused ? ParticleColor.YELLOW : color);
    }

    @Override
    public void showPreview() {
        session.addParticle(positiveParticle);
        session.addParticle(negativeParticle);
    }

    @Override
    public void hidePreview() {
        session.removeParticle(positiveParticle);
        session.removeParticle(negativeParticle);
    }

    @Override
    public boolean isValid() {
        return bone.isValid();
    }
}
