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
import me.m56738.easyarmorstands.util.Axis;
import me.m56738.easyarmorstands.util.Cursor3D;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

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
    private final Vector3d initialScale = new Vector3d();
    private final Vector3d initialCursor = new Vector3d();
    private final Vector3d initialOffset = new Vector3d();
    private final Vector3d currentOffset = new Vector3d();
    private final LineParticle axisParticle;
    private final LineParticle cursorLineParticle;
    private final PointParticle positiveParticle;
    private final PointParticle negativeParticle;
    private double currentLength;
    private Vector3d lookTarget;
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
        this.cursor = new Cursor3D(session.getPlayer(), session);
        ParticleCapability particleCapability = EasyArmorStands.getInstance().getCapability(ParticleCapability.class);
        this.axisParticle = particleCapability.createLine(session.getWorld());
        this.cursorLineParticle = EasyArmorStands.getInstance().getCapability(ParticleCapability.class).createLine(session.getWorld());
        this.cursorLineParticle.setColor(ParticleColor.WHITE);
        this.positiveParticle = particleCapability.createPoint(session.getWorld());
        this.positiveParticle.setBillboard(false);
        this.positiveParticle.setSize(Util.PIXEL);
        this.negativeParticle = particleCapability.createPoint(session.getWorld());
        this.negativeParticle.setBillboard(false);
        this.negativeParticle.setSize(Util.PIXEL);
    }

    @Override
    public void onEnter() {
        manualValue = null;
        bone.getRotation().transform(axis.getDirection(), direction).normalize();
        initialPosition.set(bone.getOrigin());
        initialScale.set(bone.getScale());
        initialCursor.set(lookTarget != null ? lookTarget : initialPosition);
        cursor.start(initialCursor);
        currentLength = length;

        updateAxisParticle();
        updateCursorLineParticle();
        session.addParticle(axisParticle);
        session.addParticle(cursorLineParticle);
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
        Vector3dc axisDirection = axis.getDirection();
        double originalScale = initialScale.dot(axisDirection);
        if (manualValue != null) {
            scale = manualValue;
            t = scale / originalScale * initialOffset.dot(direction);
        } else {
            t = currentOffset.dot(direction);
            scale = t / initialOffset.dot(direction) * originalScale;
        }
        bone.setScale(new Vector3d(
                Math.lerp(initialScale.x, scale, axisDirection.x()),
                Math.lerp(initialScale.y, scale, axisDirection.y()),
                Math.lerp(initialScale.z, scale, axisDirection.z())
        ));

        currentLength = Math.max(Math.abs(t), length);
        initialPosition.fma(t, direction, currentPosition);

        updateAxisParticle();
        updateCursorLineParticle();

        session.sendActionBar(Component.text().append(name).append(Component.text(": "))
                .append(Component.text(Util.SCALE_FORMAT.format(scale))));
    }

    @Override
    public void onExit() {
        session.removeParticle(axisParticle);
        session.removeParticle(cursorLineParticle);
        cursor.stop();
        super.onExit();
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
    public int getLookPriority() {
        return 1;
    }

    @Override
    public void update() {
        bone.getRotation().transform(axis.getDirection(), direction).normalize();
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
        return super.isValid() && bone.isValid();
    }
}
