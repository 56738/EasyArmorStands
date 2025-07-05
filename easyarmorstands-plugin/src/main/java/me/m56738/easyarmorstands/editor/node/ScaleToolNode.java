package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.tool.ScaleToolSession;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.lib.cloud.parser.ArgumentParser;
import me.m56738.easyarmorstands.lib.cloud.parser.standard.DoubleParser;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Cursor3D;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ScaleToolNode extends ToolNode implements ValueNode<Double> {
    private static final double ACTIVATION_DISTANCE = 0.2;
    protected final Session session;
    protected final ScaleToolSession toolSession;
    private final Vector3dc position;
    private final ParticleColor color;
    private final Cursor3D cursor;
    private final Vector3d delta = new Vector3d();
    private final LineParticle cursorLineParticle;
    private double distance;
    private double initialDistance;
    private boolean canActivate;
    private boolean active;
    private boolean hasManualInput;

    public ScaleToolNode(Session session, ScaleToolSession toolSession, Component name, ParticleColor color, Vector3dc position) {
        super(session, toolSession, name);
        this.session = session;
        this.toolSession = toolSession;
        this.cursorLineParticle = session.particles().createLine();
        this.position = new Vector3d(position);
        this.color = color;
        this.cursor = new Cursor3D(session);
    }

    private void activate() {
        initialDistance = distance;
        active = true;
        canActivate = false;
        cursorLineParticle.setColor(color);
    }

    @Override
    public void onEnter(@NotNull EnterContext context) {
        cursor.start(context, context.cursorOrDefault(() -> context.eyeRay().point(1)));
        distance = cursor.get().distance(position);

        cursorLineParticle.setColor(ParticleColor.GRAY);
        cursorLineParticle.setFromTo(position, cursor.get());

        session.addParticle(cursorLineParticle);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        if (hasManualInput) {
            super.onUpdate(context);
            return;
        }

        cursor.update(context);
        cursor.get().sub(position, delta);
        distance = delta.length();

        if (active) {
            double change = toolSession.snapChange(distance / initialDistance, session.snapper());
            distance = change * initialDistance;
            toolSession.setChange(change);
            delta.normalize(distance); // adjust the length of delta to the snapped value
        } else {
            canActivate = distance > ACTIVATION_DISTANCE;
            cursorLineParticle.setColor(canActivate ? ParticleColor.GRAY : ParticleColor.RED);
        }

        cursorLineParticle.setFromTo(position, delta.add(position));
        super.onUpdate(context);
    }

    @Override
    protected void updateActionBar(@NotNull UpdateContext context) {
        if (!active && !hasManualInput) {
            if (canActivate) {
                context.setActionBar(Message.component("easyarmorstands.node.scale.right-click"));
            } else {
                context.setActionBar(Message.error("easyarmorstands.node.scale.too-close"));
            }
            return;
        }
        super.updateActionBar(context);
    }

    @Override
    public boolean onClick(@NotNull ClickContext context) {
        if (context.type() == ClickContext.Type.RIGHT_CLICK && !active && !hasManualInput) {
            if (canActivate) {
                activate();
            }
            return true;
        }
        return super.onClick(context);
    }

    @Override
    public void onExit(@NotNull ExitContext context) {
        super.onExit(context);
        cursor.stop();
        session.removeParticle(cursorLineParticle);
    }

    @Override
    public Component formatValue(Double value) {
        return Component.text(Util.OFFSET_FORMAT.format(value));
    }

    @Override
    public ArgumentParser<CommandSender, Double> getParser() {
        return new DoubleParser<>(0, Double.POSITIVE_INFINITY);
    }

    @Override
    public boolean canSetValue() {
        return toolSession.canSetValue(session.player());
    }

    @Override
    public void setValue(Double value) {
        toolSession.setValue(value);
        hasManualInput = true;
        cursor.stop();
        session.removeParticle(cursorLineParticle);
    }
}
