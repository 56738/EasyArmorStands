package me.m56738.easyarmorstands.editor.node;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.DoubleArgument;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.ScaleAxis;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Cursor3D;
import me.m56738.easyarmorstands.util.EasMath;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.joml.Math;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ScaleNode extends EditorAxisNode implements ValueNode<Double> {
    private final Session session;
    private final ScaleAxis scaleAxis;
    private final Component name;
    private final LineParticle particle;
    private final LineParticle cursorLineParticle;
    private final Cursor3D cursor;
    private final Vector3d position = new Vector3d();
    private final Vector3d direction = new Vector3d();
    private final Vector3d initialCursor = new Vector3d();
    private final Vector3d temp = new Vector3d();
    private Double manualValue;
    private double initialValue;
    private double initialOffset;

    public ScaleNode(Session session, ScaleAxis scaleAxis, double length, ParticleColor color, Component name) {
        super(session, scaleAxis);
        this.session = session;
        this.scaleAxis = scaleAxis;
        this.name = name;
        this.particle = session.particleProvider().createLine();
        this.particle.setColor(color);
        this.particle.setLength(length);
        this.cursorLineParticle = session.particleProvider().createLine();
        this.cursor = new Cursor3D(session);
    }

    @Override
    public void onEnter(EnterContext context) {
        manualValue = null;
        initialValue = scaleAxis.start();

        position.set(scaleAxis.getPosition());
        Quaterniondc rotation = scaleAxis.getRotation();
        Axis axis = scaleAxis.getAxis();
        axis.getDirection().rotate(rotation, direction);

        initialCursor.set(context.cursorOrDefault(position.fma(initialValue, direction, new Vector3d())));
        cursor.start(initialCursor);

        particle.setCenter(position);
        particle.setRotation(rotation);
        particle.setAxis(axis);
        initialOffset = EasMath.getOffsetAlongLine(position, direction, initialCursor);
        cursorLineParticle.setFromTo(position.fma(initialOffset, direction, temp), initialCursor);

        session.addParticle(particle);
        session.addParticle(cursorLineParticle);

        if (Math.abs(initialOffset) < 0.01) {
            initialOffset = 0.01;
        }
        if (Math.abs(initialValue) < 0.01) {
            initialValue = 0.01;
        }
    }

    @Override
    public void onExit(ExitContext context) {
        super.onExit(context);
        cursor.stop();
        session.removeParticle(cursorLineParticle);
        session.removeParticle(particle);
    }

    @Override
    public void onUpdate(UpdateContext context) {
        cursor.update(false);
        Vector3dc cursorPosition = cursor.get();
        double offset = EasMath.getOffsetAlongLine(position, direction, cursorPosition);
        double value = Math.clamp(scaleAxis.getMinValue(), scaleAxis.getMaxValue(),
                session.snapPosition(offset) / initialOffset * initialValue);
        if (manualValue != null) {
            value = manualValue;
        }
        scaleAxis.set(value);
        Vector3dc position = scaleAxis.getPosition();
        particle.setCenter(position);
        cursorLineParticle.setFromTo(EasMath.getClosestPointOnLine(position, direction, cursorPosition, temp), cursorPosition);

        session.setActionBar(Component.text()
                .append(name)
                .append(Component.text(": "))
                .append(Component.text(Util.POSITION_FORMAT.format(value))));
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public Component formatValue(Double value) {
        return Component.text(Util.SCALE_FORMAT.format(value));
    }

    @Override
    public ArgumentParser<CommandSender, Double> getParser() {
        return new DoubleArgument.DoubleParser<>(scaleAxis.getMinValue(), scaleAxis.getMaxValue());
    }

    @Override
    public void setValue(Double value) {
        manualValue = value;
    }
}
