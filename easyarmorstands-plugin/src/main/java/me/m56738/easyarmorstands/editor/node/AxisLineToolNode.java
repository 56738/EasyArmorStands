package me.m56738.easyarmorstands.editor.node;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.DoubleArgument;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.tool.AxisToolSession;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Cursor3D;
import me.m56738.easyarmorstands.util.EasMath;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public abstract class AxisLineToolNode extends ToolNode implements ValueNode<Double> {
    private final Session session;
    private final AxisToolSession toolSession;
    private final double length;
    private final Vector3dc position;
    private final Quaterniondc rotation;
    private final Axis axis;
    private final Vector3dc direction;
    private final LineParticle particle;
    private final LineParticle cursorLineParticle;
    private final Cursor3D cursor;
    private final Vector3d initialCursor = new Vector3d();
    private final Vector3d temp = new Vector3d();
    private double initialOffset;
    private boolean hasManualInput;

    public AxisLineToolNode(Session session, AxisToolSession toolSession, Component name, ParticleColor color, double length, Vector3dc position, Quaterniondc rotation, Axis axis) {
        super(session, toolSession, name);
        this.session = session;
        this.toolSession = toolSession;
        this.length = length;
        this.position = new Vector3d(position);
        this.rotation = new Quaterniond(rotation);
        this.axis = axis;
        this.direction = axis.getDirection().rotate(rotation, new Vector3d());
        this.particle = session.particleProvider().createLine();
        this.particle.setColor(color);
        this.cursorLineParticle = session.particleProvider().createLine();
        this.cursor = new Cursor3D(session);
    }

    protected Vector3dc getDefaultCursor() {
        return position;
    }

    @Override
    public void onEnter(@NotNull EnterContext context) {
        initialCursor.set(context.cursorOrDefault(this::getDefaultCursor));
        cursor.start(context, initialCursor);

        double scale = session.getScale(position);
        particle.setCenter(position);
        particle.setRotation(rotation);
        particle.setAxis(axis);
        particle.setLength(length * scale);
        particle.setWidth(Util.LINE_WIDTH * scale);
        initialOffset = EasMath.getOffsetAlongLine(position, direction, initialCursor);
        cursorLineParticle.setFromTo(position.fma(initialOffset, direction, temp), initialCursor);
        cursorLineParticle.setWidth(Util.LINE_WIDTH * scale);

        session.addParticle(particle);
        session.addParticle(cursorLineParticle);

        hasManualInput = false;
    }

    @Override
    public void onExit(@NotNull ExitContext context) {
        super.onExit(context);
        cursor.stop();
        session.removeParticle(cursorLineParticle);
        session.removeParticle(particle);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        cursor.update(context);

        double scale = session.getScale(EasMath.getClosestPointOnLine(position, direction, context.eyeRay().origin(), temp));

        if (!hasManualInput) {
            Vector3dc cursorPosition = cursor.get();
            double offset = EasMath.getOffsetAlongLine(position, direction, cursorPosition);
            double change = toolSession.snapChange(getChange(offset, initialOffset), session.snapper());
            toolSession.setChange(change);

            double relativeOffset = offset - initialOffset;
            double min = Math.min(0, relativeOffset) - length / 2 * scale;
            double max = Math.max(0, relativeOffset) + length / 2 * scale;
            particle.setCenter(position.fma(relativeOffset, direction, temp));
            particle.setLength(max - min);
            particle.setOffset(min - relativeOffset + (max - min) / 2);
            cursorLineParticle.setFromTo(EasMath.getClosestPointOnLine(position, direction, cursorPosition, temp), cursorPosition);
        }
        particle.setWidth(Util.LINE_WIDTH * scale);
        cursorLineParticle.setWidth(Util.LINE_WIDTH * scale);
        super.onUpdate(context);
    }

    protected abstract double getChange(double currentOffset, double initialOffset);

    @Override
    public Component formatValue(Double value) {
        return Component.text(Util.OFFSET_FORMAT.format(value));
    }

    @Override
    public ArgumentParser<CommandSender, Double> getParser() {
        return new DoubleArgument.DoubleParser<>(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    @Override
    public void setValue(Double value) {
        toolSession.setValue(value);
        hasManualInput = true;
        session.removeParticle(cursorLineParticle);
        cursor.stop();
    }
}
