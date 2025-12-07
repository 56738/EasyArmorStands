package me.m56738.easyarmorstands.common.editor;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.MenuButtonProvider;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.input.Category;
import me.m56738.easyarmorstands.api.editor.input.Input;
import me.m56738.easyarmorstands.api.editor.node.ElementNode;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.node.NodeProvider;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.particle.Particle;
import me.m56738.easyarmorstands.api.particle.ParticleProvider;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.config.Configuration;
import me.m56738.easyarmorstands.common.editor.context.AddContextImpl;
import me.m56738.easyarmorstands.common.editor.context.ClickContextImpl;
import me.m56738.easyarmorstands.common.editor.context.EnterContextImpl;
import me.m56738.easyarmorstands.common.editor.context.ExitContextImpl;
import me.m56738.easyarmorstands.common.editor.context.RemoveContextImpl;
import me.m56738.easyarmorstands.common.editor.context.UpdateContextImpl;
import me.m56738.easyarmorstands.common.group.GroupMember;
import me.m56738.easyarmorstands.common.group.node.GroupRootNode;
import me.m56738.easyarmorstands.common.particle.EditorParticle;
import me.m56738.easyarmorstands.common.particle.GizmoParticleProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class SessionImpl implements Session {
    private static final Title.Times titleTimes = Title.Times.times(Duration.ZERO, Duration.ofSeconds(2), Duration.ofSeconds(1));
    private final LinkedList<Node> nodeStack = new LinkedList<>();
    private final EasyArmorStandsCommon eas;
    private final Player player;
    private final SessionSnapper snapper;
    private final Set<EditorParticle> particles = new HashSet<>();
    private final ParticleProvider particleProvider;
    private final MenuButtonProvider menuButtonProvider = new MenuButtonProviderImpl(this);
    private final NodeProvider nodeProvider;
    private final List<Input> inputs = new ArrayList<>();
    private int clickTicks = 5;
    private boolean valid = true;
    private Component currentTitle = Component.empty();
    private Component currentSubtitle = Component.empty();
    private Component currentActionBar = Component.empty();
    private int overlayTicks;
    private boolean toolRequired;
    private Category inputCategory = Category.PRIMARY;
    private boolean hasSecondaryInputs;

    public SessionImpl(EasyArmorStandsCommon eas, Player player) {
        this.eas = eas;
        this.player = player;
        this.snapper = new SessionSnapper(player);
        this.particleProvider = new GizmoParticleProvider(eas.getPlatform().getGizmoFactory(player));
        this.nodeProvider = new NodeProviderImpl(eas.getPlatform(), this);
    }

    @Override
    public Node getNode() {
        return nodeStack.peek();
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
    public @UnmodifiableView Collection<Node> getAllNodes() {
        return Collections.unmodifiableList(nodeStack);
    }

    @Override
    public double getScale(Vector3dc position) {
        Configuration config = eas.getPlatform().getConfiguration();
        Vector3dc eyePosition = player.getEyeLocation().position();
        double minDistance = config.getEditorScaleMinDistance();
        double maxDistance = config.getEditorScaleMaxDistance();
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

        if (node.onClick(context)) {
            return true;
        }

        for (Input input : inputs) {
            if (input.category() == inputCategory && input.clickType() == context.type()) {
                input.execute(context);
                return true;
            }
        }

        return false;
    }

    @Override
    public @Nullable Element getElement() {
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
            } else if (node instanceof GroupRootNode groupRootNode) {
                return groupRootNode.getGroup().getMembers().stream().map(GroupMember::getElement).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    public boolean update() {
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

        inputs.clear();
        inputs.addAll(context.getInputs());

        hasSecondaryInputs = false;
        for (Input input : inputs) {
            if (input.category() == Category.SECONDARY) {
                hasSecondaryInputs = true;
                break;
            }
        }

        inputCategory = Category.PRIMARY;
        if (player.isSneaking() && hasSecondaryInputs) {
            inputCategory = Category.SECONDARY;
        }

        updateOverlay(context);

        return player.isValid();
    }

    private void updateOverlay(UpdateContextImpl context) {
        // Resend everything once per second
        // Send changes immediately

        Component pendingActionBar = createActionBar(context.getActionBar());
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

    private Component createActionBar(Component value) {
        if (!getEasyArmorStands().getPlatform().getConfiguration().isShowInputHints()) {
            return value;
        }

        TextComponent.Builder builder = Component.text();
        builder.append(value);

        EnumSet<ClickContext.Type> seen = EnumSet.noneOf(ClickContext.Type.class);
        for (Input input : inputs) {
            if (input.category() != inputCategory) {
                continue;
            }

            ClickContext.Type clickType = input.clickType();
            if (seen.add(clickType)) {
                builder.append(createInput(getKey(clickType), input.name(), input.style()));
            }
        }

        if (inputCategory == Category.PRIMARY && hasSecondaryInputs) {
            builder.append(createInput(Component.translatable("key.sneak"), Component.translatable("easyarmorstands.input.more"), Style.style(NamedTextColor.GRAY)));
        }

        return builder.build();
    }

    private Component createInput(Component key, Component input, Style style) {
        TagResolver resolver = TagResolver.builder()
                .tag("key", Tag.selfClosingInserting(key))
                .tag("input", Tag.selfClosingInserting(input))
                .build();
        return MiniMessage.miniMessage().deserialize("  [<key>] <input>", resolver).applyFallbackStyle(style);
    }

    private Component getKey(ClickContext.Type type) {
        if (type == ClickContext.Type.LEFT_CLICK) {
            return Component.keybind("key.attack");
        } else if (type == ClickContext.Type.RIGHT_CLICK) {
            return Component.keybind("key.use");
        } else if (type == ClickContext.Type.SWAP_HANDS) {
            return Component.keybind("key.swapOffhand");
        }
        return Component.empty();
    }

    public void stop() {
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
        return eas.getPlatform().getConfiguration().getEditorButtonRange();
    }

    public double getLookThreshold() {
        return eas.getPlatform().getConfiguration().getEditorButtonThreshold();
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

    @Override
    public EasyArmorStandsCommon getEasyArmorStands() {
        return eas;
    }
}
