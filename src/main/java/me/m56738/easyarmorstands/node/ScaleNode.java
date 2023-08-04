package me.m56738.easyarmorstands.node;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.DoubleArgument;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.bone.ScaleBone;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.particle.LineParticle;
import me.m56738.easyarmorstands.particle.ParticleColor;
import me.m56738.easyarmorstands.particle.PointParticle;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Cursor3D;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ScaleNode extends EditNode implements Button, ValueNode<Double> {
    private final Session session;
    private final ScaleBone bone;
    private final Component name;
    private final Vector3d axis;
    private final Vector3d direction;
    private final ParticleColor color;
    private final double length;
    private final Cursor3D cursor;
    private final Vector3d negativeEnd = new Vector3d();
    private final Vector3d positiveEnd = new Vector3d();
    private final Vector3d currentPosition = new Vector3d();
    private final Vector3d initialPosition = new Vector3d();
    private final Vector3d initialScale = new Vector3d();
    private final Vector3d initialCursor = new Vector3d();
    private final Vector3d initialOffset = new Vector3d();
    private final Vector3d currentOffset = new Vector3d();
    private final LineParticle axisParticle;
    private final PointParticle positiveParticle;
    private final PointParticle negativeParticle;
    private Vector3d lookTarget;
    private Double manualValue;

    public ScaleNode(Session session, ScaleBone bone, Component name, Vector3dc axis, ParticleColor color, double length) {
        super(session);
        this.session = session;
        this.bone = bone;
        this.name = name;
        this.axis = new Vector3d(axis);
        this.direction = new Vector3d(axis);
        this.color = color;
        this.length = length;
        this.cursor = new Cursor3D(session.getPlayer(), session);
        ParticleCapability particleCapability = EasyArmorStands.getInstance().getCapability(ParticleCapability.class);
        this.axisParticle = particleCapability.createLine();
        this.positiveParticle = particleCapability.createPoint();
        this.negativeParticle = particleCapability.createPoint();
    }

    @Override
    public void onEnter() {
        manualValue = null;
        bone.getRotation().transform(axis, direction).normalize();
        initialPosition.set(bone.getOrigin());
        initialScale.set(bone.getScale());
        initialCursor.set(lookTarget != null ? lookTarget : initialPosition);
        cursor.start(initialCursor);
    }

    @Override
    protected void abort() {
        bone.setScale(initialScale);
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        cursor.update(false);

        initialCursor.sub(initialPosition, initialOffset);
        cursor.get().sub(initialPosition, currentOffset);
        double t;
        double scale;
        double originalScale = initialScale.dot(axis);
        if (manualValue != null) {
            scale = manualValue;
            t = scale / originalScale * initialOffset.dot(direction);
        } else {
            t = currentOffset.dot(direction);
            scale = t / initialOffset.dot(direction) * originalScale;
        }
        bone.setScale(new Vector3d(
                Math.lerp(initialScale.x, scale, axis.x),
                Math.lerp(initialScale.y, scale, axis.y),
                Math.lerp(initialScale.z, scale, axis.z)
        ));

        initialPosition.fma(t, direction, currentPosition);
        initialPosition.fma(Math.min(-length, t), direction, negativeEnd);
        initialPosition.fma(Math.max(length, t), direction, positiveEnd);
        session.showLine(negativeEnd, positiveEnd, color, true);
        session.showLine(currentPosition, cursor.get(), NamedTextColor.WHITE, false);
        session.sendActionBar(Component.text().append(name).append(Component.text(": "))
                .append(Component.text(Util.SCALE_FORMAT.format(scale))));
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
    public int getLookPriority() {
        return 1;
    }

    @Override
    public void update() {
        bone.getRotation().transform(axis, direction).normalize();
        Vector3dc position = bone.getOrigin();
        position.fma(-length, direction, negativeEnd);
        position.fma(length, direction, positiveEnd);
    }

    @Override
    public void updateLookTarget(Vector3dc eyes, Vector3dc target) {
        boolean positive = session.isLookingAtPoint(eyes, target, positiveEnd);
        boolean negative = session.isLookingAtPoint(eyes, target, negativeEnd);
        if (positive && negative) {
            if (positiveEnd.distanceSquared(eyes) < negativeEnd.distanceSquared(eyes)) {
                lookTarget = positiveEnd;
            } else {
                lookTarget = negativeEnd;
            }
        } else if (positive) {
            lookTarget = positiveEnd;
        } else if (negative) {
            lookTarget = negativeEnd;
        } else {
            lookTarget = null;
        }
    }

    @Override
    public @Nullable Vector3dc getLookTarget() {
        return lookTarget;
    }

    @Override
    public void updatePreview(boolean focused) {
        positiveParticle.setPosition(positiveEnd);
        negativeParticle.setPosition(negativeEnd);
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
        return super.isValid() && bone.isValid();
    }
}
