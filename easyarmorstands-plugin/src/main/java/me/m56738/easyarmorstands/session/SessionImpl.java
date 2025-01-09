package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.MenuButtonProvider;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.NodeProvider;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.particle.Particle;
import me.m56738.easyarmorstands.api.particle.ParticleProvider;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.group.GroupMember;
import me.m56738.easyarmorstands.group.node.GroupRootNode;
import me.m56738.easyarmorstands.particle.EditorParticle;
import me.m56738.easyarmorstands.particle.GizmoParticleProvider;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import me.m56738.easyarmorstands.session.context.AddContextImpl;
import me.m56738.easyarmorstands.session.context.ClickContextImpl;
import me.m56738.easyarmorstands.session.context.EnterContextImpl;
import me.m56738.easyarmorstands.session.context.ExitContextImpl;
import me.m56738.easyarmorstands.session.context.RemoveContextImpl;
import me.m56738.easyarmorstands.session.context.UpdateContextImpl;
import me.m56738.easyarmorstands.util.Util;
import me.m56738.gizmo.api.cursor.RayCursor;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.joml.Math;
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
import java.util.stream.Collectors;

public final class SessionImpl implements Session {
    private static final Title.Times titleTimes = Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1));
    private final LinkedList<Node> nodeStack = new LinkedList<>();
    private final Player player;
    private final Audience audience;
    private final ChangeContext context;
    private final SessionSnapper snapper;
    private final Set<EditorParticle> particles = new HashSet<>();
    private final ParticleProvider particleProvider;
    private final MenuButtonProvider menuButtonProvider = new MenuButtonProviderImpl(this);
    private final NodeProvider nodeProvider = new NodeProviderImpl(this);
    private int clickTicks = 5;
    private boolean valid = true;
    private Component currentTitle = Component.empty();
    private Component currentSubtitle = Component.empty();
    private Component currentActionBar = Component.empty();
    private int overlayTicks;
    private boolean toolRequired;

    public SessionImpl(EasPlayer context) {
        this.player = context.get();
        this.audience = context;
        this.context = context;
        this.snapper = new SessionSnapper(player);
        this.particleProvider = new GizmoParticleProvider(EasyArmorStandsPlugin.getInstance().getGizmos().player(player));
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
    public double getScale(Vector3dc position) {
        EasConfig config = EasyArmorStandsPlugin.getInstance().getConfiguration();
        Vector3d eyePosition = Util.toVector3d(player.getEyeLocation());
        double minDistance = config.editor.scale.minDistance;
        double maxDistance = config.editor.scale.maxDistance;
        if (maxDistance <= minDistance) {
            return 1;
        }
        double distance = Math.clamp(minDistance, maxDistance, eyePosition.distance(position));
        return distance / minDistance;
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
    public void returnToNode(@NotNull Node target) {
        int count = nodeStack.indexOf(target);
        for (int i = 0; i < count; i++) {
            popNode();
        }
    }

    @Override
    public void clearNodes() {
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

    private boolean hasClickCooldown(ClickContext.Type type) {
        return type != ClickContext.Type.SWAP_HANDS;
    }

    public boolean handleClick(ClickContextImpl context) {
        if (!valid) {
            return false;
        }
        Node node = nodeStack.peek();
        if (node == null) {
            return false;
        }
        if (hasClickCooldown(context.type())) {
            if (clickTicks > 0) {
                return false;
            }
            clickTicks = 5;
        }
        return node.onClick(context);
    }

    @Override
    public Element getElement() {
        for (Node node : nodeStack) {
            if (node instanceof ElementNode) {
                return ((ElementNode) node).getElement();
            }
        }
        return null;
    }

    @Override
    public @NotNull List<Element> getElements() {
        for (Node node : nodeStack) {
            if (node instanceof ElementNode) {
                return Collections.singletonList(((ElementNode) node).getElement());
            } else if (node instanceof GroupRootNode) {
                return ((GroupRootNode) node).getGroup().getMembers().stream().map(GroupMember::getElement).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
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

        for (EditorParticle particle : particles) {
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

        boolean resendOverlay = overlayTicks <= 0;
        if (resendOverlay) {
            overlayTicks = 20;
            audience.sendTitlePart(TitlePart.TIMES, titleTimes);
        }
        overlayTicks--;

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
        for (EditorParticle particle : particles) {
            particle.hide();
        }
        particles.clear();
        valid = false;
    }

    public ChangeContext context() {
        return context;
    }

    public double getRange() {
        return EasyArmorStandsPlugin.getInstance().getConfiguration().editor.button.range;
    }

    public double getLookThreshold() {
        return EasyArmorStandsPlugin.getInstance().getConfiguration().editor.button.threshold;
    }

    @Override
    public void addParticle(@NotNull Particle particle) {
        if (!valid) {
            return;
        }
        EditorParticle editorParticle = (EditorParticle) particle;
        if (particles.add(editorParticle)) {
            editorParticle.show();
        }
    }

    @Override
    public void removeParticle(@NotNull Particle particle) {
        if (!valid) {
            return;
        }
        EditorParticle editorParticle = (EditorParticle) particle;
        if (particles.remove(editorParticle)) {
            editorParticle.hide();
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

    @Override
    public @NotNull EyeRay eyeRay() {
        double length = getRange();
        Location eyeLocation = player.getEyeLocation();
        Vector3dc origin = Util.toVector3d(eyeLocation);
        Vector3dc direction = Util.toVector3d(eyeLocation.getDirection());
        double threshold = getLookThreshold();
        float yaw = eyeLocation.getYaw();
        float pitch = eyeLocation.getPitch();
        return new RayCursorEyeRay(eyeLocation.getWorld(), RayCursor.of(origin, direction, length), threshold, yaw, pitch);
    }

    public @NotNull EyeRay eyeRay(Vector2dc cursor) {
        double length = getRange();
        Location eyeLocation = player.getEyeLocation();
        Matrix4dc eyeMatrix = eyeMatrix(eyeLocation);
        Vector3d origin = eyeMatrix.transformPosition(cursor.x(), cursor.y(), 0, new Vector3d());
        Vector3dc direction = Util.toVector3d(eyeLocation.getDirection());
        double threshold = getLookThreshold();
        float yaw = eyeLocation.getYaw();
        float pitch = eyeLocation.getPitch();
        return new RayCursorEyeRay(eyeLocation.getWorld(), RayCursor.of(origin, direction, length), threshold, yaw, pitch);
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

    @Override
    public @NotNull NodeProvider nodeProvider() {
        return nodeProvider;
    }

    @Override
    public @NotNull SessionSnapper snapper() {
        return snapper;
    }

    public boolean isToolRequired() {
        return toolRequired;
    }

    public void setToolRequired(boolean toolRequired) {
        this.toolRequired = toolRequired;
    }
}
