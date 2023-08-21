package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.event.menu.SpawnMenuInitializeEvent;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.particle.AxisAlignedBoxParticle;
import me.m56738.easyarmorstands.api.particle.CircleParticle;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.Particle;
import me.m56738.easyarmorstands.api.particle.ParticleFactory;
import me.m56738.easyarmorstands.api.particle.PointParticle;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.capability.particle.ParticleCapability;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.element.ArmorStandElementType;
import me.m56738.easyarmorstands.menu.FakeLeftClick;
import me.m56738.easyarmorstands.menu.builder.SimpleMenuBuilder;
import me.m56738.easyarmorstands.menu.slot.SpawnSlot;
import me.m56738.easyarmorstands.node.ElementNode;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import me.m56738.easyarmorstands.session.context.AddContextImpl;
import me.m56738.easyarmorstands.session.context.ClickContextImpl;
import me.m56738.easyarmorstands.session.context.EnterContextImpl;
import me.m56738.easyarmorstands.session.context.ExitContextImpl;
import me.m56738.easyarmorstands.session.context.RemoveContextImpl;
import me.m56738.easyarmorstands.session.context.UpdateContextImpl;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.joml.Matrix4dc;
import org.joml.Vector2dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
    private final ParticleFactory particleFactory = new ParticleFactoryImpl();
    private int clickTicks = 5;
    private double snapIncrement = DEFAULT_SNAP_INCREMENT;
    private double angleSnapIncrement = DEFAULT_ANGLE_SNAP_INCREMENT;
    private boolean valid = true;
    private Component currentTitle = Component.empty();
    private Component currentSubtitle = Component.empty();
    private Component currentActionBar = Component.empty();
    private Component pendingTitle = Component.empty();
    private Component pendingSubtitle = Component.empty();
    private Component pendingActionBar = Component.empty();
    private int overlayTicks;

    public SessionImpl(EasPlayer context) {
        this.player = context.get();
        this.audience = context;
        this.context = context;
    }

    public static void openSpawnMenu(Player player) {
        EasPlayer context = new EasPlayer(player);
        Locale locale = context.pointers().getOrDefault(Identity.LOCALE, Locale.US);
        SimpleMenuBuilder builder = new SimpleMenuBuilder();
        if (player.hasPermission("easyarmorstands.spawn.armorstand")) {
            ArmorStandElementType type = new ArmorStandElementType();
            builder.addButton(new SpawnSlot(type, EasyArmorStands.getInstance().getConfiguration().getArmorStandButtonTemplate()));
        }
        Bukkit.getPluginManager().callEvent(new SpawnMenuInitializeEvent(player, locale, builder));
        int size = builder.getSize();
        if (size == 0) {
            return;
        }
        Component title = MiniMessage.miniMessage().deserialize(EasyArmorStands.getInstance().getConfig().getString("menu.spawn.title"));
        Menu menu = builder.build(title, locale);
        if (size == 1) {
            // Only one button, click it immediately
            MenuSlot slot = menu.getSlot(0);
            if (slot != null) {
                slot.onClick(new FakeLeftClick(menu, 0, player));
                return;
            }
        }
        player.openInventory(menu.getInventory());
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
    public <T extends Node> @Nullable T findNode(Class<T> type) {
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
        node.onEnter(new EnterContextImpl(cursor));
    }

    @Override
    public void replaceNode(@NotNull Node node) {
        if (!valid) {
            return;
        }
        Node removed = nodeStack.pop();
        removed.onExit(ExitContextImpl.INSTANCE);
        removed.onRemove(RemoveContextImpl.INSTANCE);
        nodeStack.push(node);
        node.onAdd(AddContextImpl.INSTANCE);
        node.onEnter(new EnterContextImpl(null));
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
            nodeStack.peek().onEnter(new EnterContextImpl(null));
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
        pendingTitle = Component.empty();
        pendingSubtitle = Component.empty();
        pendingActionBar = Component.empty();

        if (clickTicks > 0) {
            clickTicks--;
        }

        while (!nodeStack.isEmpty() && !nodeStack.peek().isValid()) {
            popNode();
        }

        UpdateContextImpl context = new UpdateContextImpl(eyeRay());
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

        updateOverlay();

        return player.isValid();
    }

    private void updateOverlay() {
        // Resend everything once per second
        // Send changes immediately

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
    public void addParticle(Particle particle) {
        if (!valid) {
            return;
        }
        if (particles.add(particle)) {
            particle.show(player);
        }
    }

    @Override
    public void removeParticle(Particle particle) {
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
    public void setTitle(ComponentLike title) {
        pendingTitle = title.asComponent();
    }

    @Override
    public void setSubtitle(ComponentLike subtitle) {
        pendingSubtitle = subtitle.asComponent();
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
        Vector3dc target = origin.fma(length, Util.toVector3d(eyeLocation.getDirection()), new Vector3d());
        double threshold = getLookThreshold();
        return new EyeRayImpl(origin, target, length, threshold);
    }

    @Override
    public @NotNull EyeRay eyeRay(Vector2dc cursor) {
        double length = getRange();
        Matrix4dc eyeMatrix = eyeMatrix();
        Vector3d origin = eyeMatrix.transformPosition(cursor.x(), cursor.y(), 0, new Vector3d());
        Vector3d target = eyeMatrix.transformDirection(0, 0, 1, new Vector3d())
                .mulAdd(length, origin);
        double threshold = getLookThreshold();
        return new EyeRayImpl(origin, target, length, threshold);
    }

    @Override
    public @NotNull Matrix4dc eyeMatrix() {
        return Util.toMatrix4d(player.getEyeLocation());
    }

    @Override
    public @NotNull ParticleFactory particleFactory() {
        return particleFactory;
    }

    @Override
    public void setActionBar(ComponentLike actionBar) {
        pendingActionBar = actionBar.asComponent();
    }

    public static class EyeRayImpl implements EyeRay {
        private final Vector3dc origin;
        private final Vector3dc target;
        private final double length;
        private final double threshold;

        public EyeRayImpl(Vector3dc origin, Vector3dc target, double length, double threshold) {
            this.origin = origin;
            this.target = target;
            this.length = length;
            this.threshold = threshold;
        }

        @Override
        public Vector3dc origin() {
            return origin;
        }

        @Override
        public Vector3dc target() {
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
    }

    private class ParticleFactoryImpl implements ParticleFactory {
        private final ParticleCapability particleCapability;

        private ParticleFactoryImpl() {
            this.particleCapability = EasyArmorStands.getInstance().getCapability(ParticleCapability.class);
        }

        @Override
        public PointParticle createPoint() {
            return particleCapability.createPoint(getWorld());
        }

        @Override
        public LineParticle createLine() {
            return particleCapability.createLine(getWorld());
        }

        @Override
        public CircleParticle createCircle() {
            return particleCapability.createCircle(getWorld());
        }

        @Override
        public AxisAlignedBoxParticle createAxisAlignedBox() {
            return particleCapability.createAxisAlignedBox(getWorld());
        }
    }
}
