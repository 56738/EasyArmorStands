package me.m56738.easyarmorstands.editor.node;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.DoubleArgument;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleToolSession;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class AxisScaleToolNode extends AxisToolNode implements ValueNode<Double> {
    private final Session session;
    private final AxisScaleToolSession toolSession;
    private final Component name;
    private final double initialValue;
    private Double manualValue;

    public AxisScaleToolNode(Session session, AxisScaleToolSession toolSession, Component name, ParticleColor color, double length, Vector3dc position, Quaterniondc rotation, Axis axis, double initialValue) {
        super(session, toolSession, name, color, length, position, rotation, axis, false);
        this.session = session;
        this.toolSession = toolSession;
        this.name = name;
        if (Math.abs(initialValue) >= 0.01) {
            this.initialValue = initialValue;
        } else {
            this.initialValue = 0.01;
        }
    }

    @Override
    public void onEnter(@NotNull EnterContext context) {
        super.onEnter(context);
        manualValue = null;
    }

    @Override
    protected double update(double currentOffset, double initialOffset) {
        if (initialOffset >= 0) {
            initialOffset = Math.max(initialOffset, 0.01);
        } else {
            initialOffset = Math.min(initialOffset, -0.01);
        }
        double snappedOffset = session.snapPosition(currentOffset - initialOffset) + initialOffset;
        double value = snappedOffset / initialOffset;
        if (manualValue != null) {
            value = manualValue;
        }
        toolSession.setScale(value);
        return value;
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public Component formatValue(Double value) {
        return Component.text(Util.SCALE_FORMAT.format(value * initialValue));
    }

    @Override
    public ArgumentParser<CommandSender, Double> getParser() {
        return new DoubleArgument.DoubleParser<>(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    @Override
    public void setValue(Double value) {
        manualValue = value / initialValue;
    }
}
