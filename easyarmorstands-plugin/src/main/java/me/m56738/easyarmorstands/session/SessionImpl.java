package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.CarryButtonBuilder;
import me.m56738.easyarmorstands.api.editor.button.MenuButtonProvider;
import me.m56738.easyarmorstands.api.editor.button.MoveButtonBuilder;
import me.m56738.easyarmorstands.api.editor.button.RotateButtonBuilder;
import me.m56738.easyarmorstands.api.editor.button.ScaleButtonBuilder;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.particle.AxisAlignedBoxParticle;
import me.m56738.easyarmorstands.api.particle.CircleParticle;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.Particle;
import me.m56738.easyarmorstands.api.particle.ParticleProvider;
import me.m56738.easyarmorstands.api.particle.PointParticle;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.editor.button.CarryButtonBuilderImpl;
import me.m56738.easyarmorstands.editor.button.MoveButtonBuilderImpl;
import me.m56738.easyarmorstands.editor.button.RotateButtonBuilderImpl;
import me.m56738.easyarmorstands.editor.button.ScaleButtonBuilderImpl;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import me.m56738.easyarmorstands.session.context.AddContextImpl;
import me.m56738.easyarmorstands.session.context.ClickContextImpl;
import me.m56738.easyarmorstands.session.context.EnterContextImpl;
import me.m56738.easyarmorstands.session.context.ExitContextImpl;
import me.m56738.easyarmorstands.session.context.RemoveContextImpl;
import me.m56738.easyarmorstands.session.context.UpdateContextImpl;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector2dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class SessionImpl implements Session {
    public static final double DEFAULT_SNAP_INCREMENT = 1.0 / 32;
    public static final double DEFAULT_ANGLE_SNAP_INCREMENT = 360.0 / 256;
    private static final Title.Times titleTimes = Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1));
    private final LinkedList<Node> nodeStack = new LinkedList<>();
    private final Player player;
    private final Audience audience;
    private final ChangeContext context;
    private final Set<Particle> particles = new HashSet<>();
    private final ParticleProvider particleProvider = new ParticleProviderImpl(this);
    private final MenuButtonProvider menuButtonProvider = new MenuButtonProviderImpl(this);
    private int clickTicks = 5;
    private double snapIncrement = DEFAULT_SNAP_INCREMENT;
    private double angleSnapIncrement = DEFAULT_ANGLE_SNAP_INCREMENT;
    private boolean valid = true;
    private Component currentTitle = Component.empty();
    private Component currentSubtitle = Component.empty();
    private Component currentActionBar = Component.empty();
    private int overlayTicks;

    public SessionImpl(EasPlayer context) {
        this.player = context.get();
        this.audience = context;
        this.context = context;
    }

    @Override
    public Node getNode() {
        return nodeStack.peek();
    }

    public @UnmodifiableView List<Node> getNodeStack() {
        return Collections.unmodifiableList(nodeStack);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Node> @Nullable T findNode(@NotNull Class<T> type) {
        for (Node node : nodeStack) {
            if (type.isAssignableFrom(node.getClass())) {
                return (T) node;
            }
        }
        return null;
    }

    @Override
    public void pushNode(@NotNull Node node) {
        pushNode(node, null);
    }

    @Override
    public void pushNode(@NotNull Node node, @Nullable Vector3dc cursor) {
        if (!valid) {
            return;
        }
        if (!nodeStack.isEmpty()) {
            nodeStack.peek().onExit(ExitContextImpl.INSTANCE);
        }
        nodeStack.push(node);
        node.onAdd(AddContextImpl.INSTANCE);
        node.onEnter(new EnterContextImpl(this, cursor));
    }

    @Override
    public void popNode() {
        if (!valid) {
            return;
        }
        Node removed = nodeStack.pop();
        removed.onExit(ExitContextImpl.INSTANCE);
        removed.onRemove(RemoveContextImpl.INSTANCE);
        if (!nodeStack.isEmpty()) {
            nodeStack.peek().onEnter(new EnterContextImpl(this, null));
        }
    }

    @Override
    public void clearNode() {
        if (!valid) {
            return;
        }
        if (!nodeStack.isEmpty()) {
            nodeStack.peek().onExit(ExitContextImpl.INSTANCE);
        }
        for (Node node : nodeStack) {
            node.onRemove(RemoveContextImpl.INSTANCE);
        }
        nodeStack.clear();
    }

    public boolean handleClick(ClickContextImpl context) {
        if (!valid) {
            return false;
        }
        Node node = nodeStack.peek();
        if (node == null || clickTicks > 0) {
            return false;
        }
        clickTicks = 5;
        return node.onClick(context);
    }

    @Override
    public double snapPosition(double value) {
        if (player.isSneaking()) {
            return value;
        }
        return Util.snap(value, snapIncrement);
    }

    @Override
    public double snapAngle(double value) {
        if (player.isSneaking()) {
            return value;
        }
        return Util.snap(value, angleSnapIncrement);
    }

    @Override
    public Element getElement() {
        for (Node node : nodeStack) {
            if (node instanceof ElementNode) {
                Element element = ((ElementNode) node).getElement();
                if (element != null) {
                    return element;
                }
            }
        }
        return null;
    }

    boolean update() {
        if (clickTicks > 0) {
            clickTicks--;
        }

        while (!nodeStack.isEmpty() && !nodeStack.peek().isValid()) {
            popNode();
        }

        UpdateContextImpl context = new UpdateContextImpl(this);
        Node currentNode = nodeStack.peek();
        if (currentNode != null) {
            currentNode.onUpdate(context);
        }
        for (Node node : nodeStack) {
            if (node != currentNode) {
                node.onInactiveUpdate(context);
            }
        }

        for (Particle particle : particles) {
            particle.update();
        }

        updateOverlay(context);

        return player.isValid();
    }

    private void updateOverlay(UpdateContextImpl context) {
        // Resend everything once per second
        // Send changes immediately

        Component pendingActionBar = context.getActionBar();
        Component pendingTitle = context.getTitle();
        Component pendingSubtitle = context.getSubtitle();

        boolean resendOverlay = overlayTicks >= 20;
        if (resendOverlay) {
            overlayTicks = 0;
            audience.sendTitlePart(TitlePart.TIMES, titleTimes);
        }
        overlayTicks++;

        if (resendOverlay || !Objects.equals(currentTitle, pendingTitle) || !Objects.equals(currentSubtitle, pendingSubtitle)) {
            currentTitle = pendingTitle;
            currentSubtitle = pendingSubtitle;
            audience.sendTitlePart(TitlePart.SUBTITLE, currentSubtitle);
            audience.sendTitlePart(TitlePart.TITLE, currentTitle);
        }

        if (resendOverlay || !Objects.equals(currentActionBar, pendingActionBar)) {
            currentActionBar = pendingActionBar;
            audience.sendActionBar(currentActionBar);
        }
    }

    void stop() {
        Node currentNode = nodeStack.peek();
        if (currentNode != null) {
            currentNode.onExit(ExitContextImpl.INSTANCE);
        }
        for (Node node : nodeStack) {
            node.onRemove(RemoveContextImpl.INSTANCE);
        }
        nodeStack.clear();
        audience.clearTitle();
        audience.sendActionBar(Component.empty());
        for (Particle particle : particles) {
            particle.hide(player);
        }
        particles.clear();
        valid = false;
    }

    public ChangeContext context() {
        return context;
    }

    public World getWorld() {
        return player.getWorld();
    }

    public double getRange() {
        return 10;
    }

    public double getLookThreshold() {
        return 0.1;
    }

    public double getSnapIncrement() {
        return snapIncrement;
    }

    public void setSnapIncrement(double snapIncrement) {
        this.snapIncrement = snapIncrement;
    }

    public double getAngleSnapIncrement() {
        return angleSnapIncrement;
    }

    public void setAngleSnapIncrement(double angleSnapIncrement) {
        this.angleSnapIncrement = angleSnapIncrement;
    }

    @Override
    public void addParticle(@NotNull Particle particle) {
        if (!valid) {
            return;
        }
        if (particles.add(particle)) {
            particle.show(player);
        }
    }

    @Override
    public void removeParticle(@NotNull Particle particle) {
        if (!valid) {
            return;
        }
        if (particles.remove(particle)) {
            particle.hide(player);
        }
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public @NotNull Player player() {
        return player;
    }

    @Override
    public @NotNull PropertyContainer properties(@NotNull Element element) {
        return new TrackedPropertyContainer(element, context);
    }

    public @NotNull EyeRay eyeRay() {
        double length = getRange();
        Location eyeLocation = player.getEyeLocation();
        Matrix4dc eyeMatrix = eyeMatrix(eyeLocation);
        Vector3dc origin = Util.toVector3d(eyeLocation);
        Vector3dc target = origin.fma(length, Util.toVector3d(eyeLocation.getDirection()), new Vector3d());
        double threshold = getLookThreshold();
        float yaw = eyeLocation.getYaw();
        float pitch = eyeLocation.getPitch();
        return new EyeRayImpl(origin, target, length, threshold, yaw, pitch, eyeMatrix);
    }

    public @NotNull EyeRay eyeRay(Vector2dc cursor) {
        double length = getRange();
        Location eyeLocation = player.getEyeLocation();
        Matrix4dc eyeMatrix = eyeMatrix(eyeLocation);
        Vector3d origin = eyeMatrix.transformPosition(cursor.x(), cursor.y(), 0, new Vector3d());
        Vector3d target = eyeMatrix.transformDirection(0, 0, 1, new Vector3d())
                .mulAdd(length, origin);
        double threshold = getLookThreshold();
        float yaw = eyeLocation.getYaw();
        float pitch = eyeLocation.getPitch();
        return new EyeRayImpl(origin, target, length, threshold, yaw, pitch, eyeMatrix);
    }

    private Matrix4dc eyeMatrix(Location eyeLocation) {
        return Util.toMatrix4d(eyeLocation);
    }

    @Override
    public @NotNull ParticleProvider particleProvider() {
        return particleProvider;
    }

    @Override
    public @NotNull MenuButtonProvider menuEntryProvider() {
        return menuButtonProvider;
    }

    public static class EyeRayImpl implements EyeRay {
        private final Vector3dc origin;
        private final Vector3dc target;
        private final double length;
        private final double threshold;
        private final float yaw;
        private final float pitch;
        private final Matrix4dc matrix;
        private Matrix4dc inverseMatrix;

        public EyeRayImpl(Vector3dc origin, Vector3dc target, double length, double threshold, float yaw, float pitch, Matrix4dc matrix) {
            this.origin = origin;
            this.target = target;
            this.length = length;
            this.threshold = threshold;
            this.yaw = yaw;
            this.pitch = pitch;
            this.matrix = matrix;
        }

        @Override
        public @NotNull Vector3dc origin() {
            return origin;
        }

        @Override
        public @NotNull Vector3dc target() {
            return target;
        }

        @Override
        public double length() {
            return length;
        }

        @Override
        public double threshold() {
            return threshold;
        }

        @Override
        public float yaw() {
            return yaw;
        }

        @Override
        public float pitch() {
            return pitch;
        }

        @Override
        public @NotNull Matrix4dc matrix() {
            return matrix;
        }

        @Override
        public @NotNull Matrix4dc inverseMatrix() {
            if (inverseMatrix == null) {
                inverseMatrix = matrix.invert(new Matrix4d());
            }
            return inverseMatrix;
        }
    }

    private static class ParticleProviderImpl implements ParticleProvider {
        private final SessionImpl session;
        private final ParticleCapability particleCapability;

        private ParticleProviderImpl(SessionImpl session) {
            this.session = session;
            this.particleCapability = EasyArmorStandsPlugin.getInstance().getCapability(ParticleCapability.class);
        }

        @Override
        public @NotNull PointParticle createPoint() {
            return particleCapability.createPoint(session.getWorld());
        }

        @Override
        public @NotNull LineParticle createLine() {
            return particleCapability.createLine(session.getWorld());
        }

        @Override
        public @NotNull CircleParticle createCircle() {
            return particleCapability.createCircle(session.getWorld());
        }

        @Override
        public @NotNull AxisAlignedBoxParticle createAxisAlignedBox() {
            return particleCapability.createAxisAlignedBox(session.getWorld());
        }
    }

    private static class MenuButtonProviderImpl implements MenuButtonProvider {
        private final Session session;

        private MenuButtonProviderImpl(Session session) {
            this.session = session;
        }

        @Override
        public MoveButtonBuilder move() {
            return new MoveButtonBuilderImpl(session);
        }

        @Override
        public ScaleButtonBuilder scale() {
            return new ScaleButtonBuilderImpl(session);
        }

        @Override
        public RotateButtonBuilder rotate() {
            return new RotateButtonBuilderImpl(session);
        }

        @Override
        public CarryButtonBuilder carry() {
            return new CarryButtonBuilderImpl(session);
        }
    }
}
