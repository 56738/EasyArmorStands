package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.tool.ScalarToolSession;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.lib.cloud.parser.ArgumentParser;
import me.m56738.easyarmorstands.lib.cloud.parser.standard.DoubleParser;
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
    private final ScalarToolSession toolSession;
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

    public AxisLineToolNode(Session session, ScalarToolSession toolSession, Component name, ParticleColor color, double length, Vector3dc position, Quaterniondc rotation, Axis axis) {
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
        initialOffset = EasMath.getOffsetAlongLine(position, direction, initialCursor);
        cursor.start(context, initialCursor);

        double scale = session.getScale(position);
        particle.setRotation(rotation);
        particle.setAxis(axis);

        updateParticles(initialOffset, scale);

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
            double offset = EasMath.getOffsetAlongLine(position, direction, cursor.get());
            double change = toolSession.snapChange(getChange(offset, initialOffset), session.snapper());
            toolSession.setChange(change);
            updateParticles(offset, scale);
        } else {
            updateParticleScales(scale);
        }

        super.onUpdate(context);
    }

    private void updateParticles(double offset, double scale) {
        double min = Math.min(0, offset) - length / 2 * scale;
        double max = Math.max(0, offset) + length / 2 * scale;
        particle.setCenter(position.fma(offset, direction, temp));
        particle.setLength(max - min);
        particle.setOffset(min - offset + (max - min) / 2);
        cursorLineParticle.setFromTo(position.fma(offset, direction, temp), cursor.get());
        updateParticleScales(scale);
    }

    private void updateParticleScales(double scale) {
        particle.setWidth(Util.LINE_WIDTH * scale);
        cursorLineParticle.setWidth(Util.LINE_WIDTH * scale);
    }

    protected abstract double getChange(double currentOffset, double initialOffset);

    @Override
    public Component formatValue(Double value) {
        return Component.text(Util.OFFSET_FORMAT.format(value));
    }

    @Override
    public ArgumentParser<CommandSender, Double> getParser() {
        return new DoubleParser<>(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    @Override
    public boolean canSetValue() {
        return toolSession.canSetValue(session.player());
    }

    @Override
    public void setValue(Double value) {
        toolSession.setValue(value);
        hasManualInput = true;
        session.removeParticle(cursorLineParticle);
        cursor.stop();
    }
}
