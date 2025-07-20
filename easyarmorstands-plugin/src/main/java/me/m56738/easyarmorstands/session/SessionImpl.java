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
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import me.m56738.easyarmorstands.config.EasConfig;
import me.m56738.easyarmorstands.group.GroupMember;
import me.m56738.easyarmorstands.group.node.GroupRootNode;
import me.m56738.easyarmorstands.particle.EditorParticle;
import me.m56738.easyarmorstands.particle.GizmoParticleProvider;
import me.m56738.easyarmorstands.session.context.AddContextImpl;
import me.m56738.easyarmorstands.session.context.ClickContextImpl;
import me.m56738.easyarmorstands.session.context.EnterContextImpl;
import me.m56738.easyarmorstands.session.context.ExitContextImpl;
import me.m56738.easyarmorstands.session.context.RemoveContextImpl;
import me.m56738.easyarmorstands.session.context.UpdateContextImpl;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.joml.Math;
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

    public SessionImpl(CommonPlatform platform, Player player) {
        this.player = player;
        this.snapper = new SessionSnapper(player);
        this.particleProvider = new GizmoParticleProvider(platform.getGizmoFactory(player));
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
        Vector3dc eyePosition = player.getEyeLocation().position();
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
            particle.updateGizmo();
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
            player.sendTitlePart(TitlePart.TIMES, titleTimes);
        }
        overlayTicks--;

        if (resendOverlay || !Objects.equals(currentTitle, pendingTitle) || !Objects.equals(currentSubtitle, pendingSubtitle)) {
            currentTitle = pendingTitle;
            currentSubtitle = pendingSubtitle;
            player.sendTitlePart(TitlePart.SUBTITLE, currentSubtitle);
            player.sendTitlePart(TitlePart.TITLE, currentTitle);
        }

        if (resendOverlay || !Objects.equals(currentActionBar, pendingActionBar)) {
            currentActionBar = pendingActionBar;
            player.sendActionBar(currentActionBar);
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
        player.clearTitle();
        player.sendActionBar(Component.empty());
        for (EditorParticle particle : particles) {
            particle.hideGizmo();
        }
        particles.clear();
        valid = false;
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
            editorParticle.showGizmo();
        }
    }

    @Override
    public void removeParticle(@NotNull Particle particle) {
        if (!valid) {
            return;
        }
        EditorParticle editorParticle = (EditorParticle) particle;
        if (particles.remove(editorParticle)) {
            editorParticle.hideGizmo();
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
    public @NotNull EyeRay eyeRay() {
        double length = getRange();
        Location eyeLocation = player.getEyeLocation();
        double threshold = getLookThreshold();
        return new EyeRayImpl(eyeLocation.world(), eyeLocation, length, threshold);
    }

    public @NotNull EyeRay eyeRay(Vector2dc cursor) {
        double length = getRange();
        Location eyeLocation = player.getEyeLocation();
        double threshold = getLookThreshold();
        Vector3d origin = eyeLocation.matrix().transformPosition(cursor.x(), cursor.y(), 0, new Vector3d());
        return new EyeRayImpl(eyeLocation.world(), eyeLocation.withPosition(origin), length, threshold);
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
