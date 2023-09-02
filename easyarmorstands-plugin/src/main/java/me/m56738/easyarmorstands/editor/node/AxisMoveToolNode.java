package me.m56738.easyarmorstands.editor.node;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.DoubleArgument;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveToolSession;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class AxisMoveToolNode extends AxisToolNode implements ValueNode<Double> {
    private final Session session;
    private final AxisMoveToolSession toolSession;
    private final Component name;
    private final Double initialValue;
    private Double manualValue;

    public AxisMoveToolNode(Session session, AxisMoveToolSession toolSession, Component name, ParticleColor color, double length, Vector3dc position, Quaterniondc rotation, Axis axis, Double initialValue, boolean inverted) {
        super(session, toolSession, name, color, length, position, rotation, axis, inverted);
        this.session = session;
        this.toolSession = toolSession;
        this.name = name;
        this.initialValue = initialValue;
    }

    @Override
    public void onEnter(@NotNull EnterContext context) {
        super.onEnter(context);
        manualValue = null;
    }

    @Override
    protected double update(double currentOffset, double initialOffset) {
        double value = currentOffset - initialOffset;
        if (manualValue != null) {
            value = manualValue;
        } else if (initialValue != null) {
            value = session.snapPosition(value + initialValue) - initialValue;
        } else {
            value = session.snapPosition(value);
        }
        toolSession.setChange(value);
        return value;
    }

    @Override
    public Component formatValue(Double value) {
        if (initialValue != null) {
            return Component.text(Util.POSITION_FORMAT.format(value + initialValue));
        }
        return Component.text(Util.OFFSET_FORMAT.format(value));
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public ArgumentParser<CommandSender, Double> getParser() {
        return new DoubleArgument.DoubleParser<>(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    @Override
    public void setValue(Double value) {
        manualValue = value;
        if (initialValue != null) {
            manualValue -= initialValue;
        }
    }
}
