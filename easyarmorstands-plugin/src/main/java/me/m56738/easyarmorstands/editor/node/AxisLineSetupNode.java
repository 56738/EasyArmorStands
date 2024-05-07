package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.ScalarToolSession;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.particle.PointParticle;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public abstract class AxisLineSetupNode implements Node {
    private static final double ACTIVATION_DISTANCE = 0.5;

    private final Session session;
    private final ScalarToolSession toolSession;
    private final Vector3dc position;
    private final Axis axis;
    private final PointParticle cursorParticle;
    private final LineParticle particle;
    private final Vector3d cursor = new Vector3d();
    private final Vector3d delta = new Vector3d();
    private boolean valid;

    public AxisLineSetupNode(Session session, ScalarToolSession toolSession, Vector3dc position, Axis axis) {
        this.session = session;
        this.toolSession = toolSession;
        this.position = position;
        this.axis = axis;
        this.cursorParticle = session.particleProvider().createPoint();
        this.particle = session.particleProvider().createLine();
    }

    private void update() {
        cursor.set(session.eyeRay().projectPoint(position));
        cursor.sub(position, delta);
        valid = delta.length() >= ACTIVATION_DISTANCE;
    }

    @Override
    public void onEnter(@NotNull EnterContext context) {
        update();

        cursorParticle.setPosition(cursor);
        cursorParticle.setColor(ParticleColor.YELLOW);

        double scale = session.getScale(position);
        particle.setAxis(axis);
        particle.setFromTo(position, cursor);
        particle.setWidth(Util.LINE_WIDTH * scale);

        session.addParticle(cursorParticle);
        session.addParticle(particle);
    }

    @Override
    public void onExit(@NotNull ExitContext context) {
        session.removeParticle(cursorParticle);
        session.removeParticle(particle);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        update();

        cursorParticle.setPosition(cursor);

        double scale = session.getScale(position);
        particle.setFromTo(position, cursor);
        particle.setWidth(Util.LINE_WIDTH * scale);

        if (valid) {
            particle.setColor(ParticleColor.GRAY);
            context.setActionBar(Message.component("easyarmorstands.node.axis.right-click"));
        } else {
            particle.setColor(ParticleColor.RED);
            context.setActionBar(Message.component("easyarmorstands.node.axis.too-close").color(NamedTextColor.RED));
        }
    }

    @Override
    public boolean onClick(@NotNull ClickContext context) {
        if (context.type() == ClickContext.Type.RIGHT_CLICK) {
            Node node = createNode();
            if (node != null) {
                session.popNode();
                session.pushNode(node, cursor);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isValid() {
        return toolSession.isValid();
    }

    public Node createNodeOrSetup() {
        Node node = createNode();
        if (node != null) {
            return node;
        }
        return this;
    }

    public @Nullable Node createNode() {
        update();
        if (!valid) {
            return null;
        }
        Quaterniondc rotation = new Quaterniond().rotationTo(axis.getDirection(), delta);
        return createNode(rotation, axis, cursor);
    }

    protected abstract Node createNode(Quaterniondc rotation, Axis axis, Vector3dc cursor);
}
