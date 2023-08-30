package me.m56738.easyarmorstands.editor.node;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.DoubleArgument;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.LineAxis;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Cursor3D;
import me.m56738.easyarmorstands.util.EasMath;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public abstract class LineNode extends EditorAxisNode implements ValueNode<Double> {
    private final Session session;
    private final LineAxis lineAxis;
    private final Component name;
    private final LineParticle particle;
    private final LineParticle cursorLineParticle;
    private final Cursor3D cursor;
    private final Vector3d position = new Vector3d();
    private final Quaterniond rotation = new Quaterniond();
    private final Vector3d direction = new Vector3d();
    private final Vector3d initialCursor = new Vector3d();
    private final Vector3d temp = new Vector3d();
    private Axis axis;
    private Double manualValue;
    private double initialValue;
    private double initialOffset;

    public LineNode(Session session, LineAxis lineAxis, double length, ParticleColor color, Component name) {
        super(session, lineAxis);
        this.session = session;
        this.lineAxis = lineAxis;
        this.name = name;
        this.particle = session.particleProvider().createLine();
        this.particle.setColor(color);
        this.particle.setLength(length);
        this.cursorLineParticle = session.particleProvider().createLine();
        this.cursor = new Cursor3D(session);
    }

    protected Vector3dc getPosition() {
        return position;
    }

    protected Quaterniondc getRotation() {
        return rotation;
    }

    protected Vector3dc getDirection() {
        return direction;
    }

    protected double getInitialValue() {
        return initialValue;
    }

    protected double getInitialOffset() {
        return initialOffset;
    }

    protected abstract Vector3dc getDefaultCursor();

    protected abstract double getValue(double offset);

    @Override
    public void onEnter(@NotNull EnterContext context) {
        manualValue = null;
        initialValue = lineAxis.start();

        position.set(lineAxis.getPosition());
        rotation.set(lineAxis.getRotation());
        axis = lineAxis.getAxis();
        axis.getDirection().rotate(rotation, direction);
        if (lineAxis.isInverted()) {
            direction.negate();
        }

        initialCursor.set(context.cursorOrDefault(this::getDefaultCursor));
        cursor.start(context, initialCursor);

        particle.setCenter(position);
        particle.setRotation(rotation);
        particle.setAxis(axis);
        initialOffset = EasMath.getOffsetAlongLine(position, direction, initialCursor);
        cursorLineParticle.setFromTo(position.fma(initialOffset, direction, temp), initialCursor);

        session.addParticle(particle);
        session.addParticle(cursorLineParticle);
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
        Vector3dc cursorPosition = cursor.get();
        double offset = EasMath.getOffsetAlongLine(position, direction, cursorPosition);
        double value;
        if (manualValue != null) {
            value = manualValue;
        } else {
            value = Math.clamp(lineAxis.getMinValue(), lineAxis.getMaxValue(), getValue(offset));
        }
        lineAxis.set(value);
        Vector3dc position = lineAxis.getPosition();
        particle.setCenter(position);
        cursorLineParticle.setFromTo(EasMath.getClosestPointOnLine(position, direction, cursorPosition, temp), cursorPosition);

        context.setActionBar(Component.text()
                .append(name)
                .append(Component.text(": "))
                .append(formatValue(value)));
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public ArgumentParser<CommandSender, Double> getParser() {
        return new DoubleArgument.DoubleParser<>(lineAxis.getMinValue(), lineAxis.getMaxValue());
    }

    @Override
    public void setValue(Double value) {
        manualValue = value;
    }
}
