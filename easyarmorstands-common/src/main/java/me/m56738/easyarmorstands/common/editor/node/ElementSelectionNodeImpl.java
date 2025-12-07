package me.m56738.easyarmorstands.common.editor.node;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.AbstractNode;
import me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.ElementDiscoverySource;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.particle.BoundingBoxParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.common.editor.input.OpenSpawnMenuInput;
import me.m56738.easyarmorstands.common.editor.input.selection.AddElementToGroupInput;
import me.m56738.easyarmorstands.common.editor.input.selection.ClearGroupSelectionInput;
import me.m56738.easyarmorstands.common.editor.input.selection.RemoveElementFromGroupInput;
import me.m56738.easyarmorstands.common.editor.input.selection.SelectElementInput;
import me.m56738.easyarmorstands.common.editor.input.selection.SelectGroupInput;
import me.m56738.easyarmorstands.common.editor.input.selection.box.CancelBoxSelectionInput;
import me.m56738.easyarmorstands.common.editor.input.selection.box.ConfirmBoxSelectionInput;
import me.m56738.easyarmorstands.common.editor.input.selection.box.StartBoxSelectionInput;
import me.m56738.easyarmorstands.common.group.Group;
import me.m56738.easyarmorstands.common.group.node.GroupRootNode;
import me.m56738.easyarmorstands.common.message.Message;
import me.m56738.easyarmorstands.common.message.MessageStyle;
import me.m56738.easyarmorstands.common.permission.Permissions;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ElementSelectionNodeImpl extends AbstractNode implements ElementSelectionNode {
    private final CommonPlatform platform;
    private final Session session;
    private final Map<ElementDiscoveryEntry, ElementEntry> entries = new HashMap<>();
    private final Component name;
    private final Set<ElementDiscoverySource> sources = new LinkedHashSet<>();
    private final Map<ElementDiscoveryEntry, SelectableElement> groupMembers = new LinkedHashMap<>();
    private final Map<ElementDiscoveryEntry, SelectableElement> selectionBoxMembers = new LinkedHashMap<>();
    private final Vector3d selectionBoxOrigin = new Vector3d();
    private final double selectionCursorOffset = 2.0;
    private final BoundingBoxParticle selectionBoxParticle;
    private final OpenSpawnMenuInput openSpawnMenuInput;
    private final boolean allowGroups;
    private final boolean allowSpawn;
    private final double boxSizeLimit;
    private final int buttonLimit;
    private final int groupLimit;
    private @Nullable BoundingBox selectionBox;
    private boolean selectionBoxEditing;
    private double range;
    private int buttonCount;

    public ElementSelectionNodeImpl(CommonPlatform platform, Session session) {
        super(session);
        this.platform = platform;
        this.session = session;
        this.name = Message.component("easyarmorstands.node.select-entity");
        this.selectionBoxParticle = session.particleProvider().createAxisAlignedBox();
        this.openSpawnMenuInput = new OpenSpawnMenuInput(platform, session);
        this.allowGroups = session.player().hasPermission(Permissions.GROUP);
        this.allowSpawn = session.player().hasPermission(Permissions.SPAWN);
        this.boxSizeLimit = platform.getConfiguration().getGroupBoxSize();
        this.buttonLimit = platform.getConfiguration().getSelectionButtonLimit();
        this.groupLimit = platform.getConfiguration().getGroupSizeLimit();
        this.range = platform.getConfiguration().getEditorButtonRange();
    }

    @Override
    public double getRange() {
        return range;
    }

    @Override
    public void setRange(double range) {
        this.range = range;
    }

    @Override
    public void addSource(ElementDiscoverySource source) {
        sources.add(source);
    }

    @Override
    public void removeSource(ElementDiscoverySource source) {
        sources.remove(source);
    }

    @Override
    public @Unmodifiable Iterable<ElementDiscoverySource> getSources() {
        return Collections.unmodifiableSet(new HashSet<>(sources));
    }

    public void startBoxSelection(EyeRay eyeRay) {
        selectionBoxOrigin.set(eyeRay.point(selectionCursorOffset));
        if (selectionBox != null) {
            session.removeParticle(selectionBoxParticle);
        }
        selectionBox = BoundingBox.of(selectionBoxOrigin);
        selectionBoxParticle.setBoundingBox(selectionBox);
        selectionBoxParticle.setColor(ParticleColor.YELLOW);
        selectionBoxEditing = true;
        session.addParticle(selectionBoxParticle);
    }

    public void finishBoxSelection() {
        selectionBoxEditing = false;
        groupMembers.putAll(selectionBoxMembers);
        selectionBoxMembers.clear();
        selectionBoxParticle.setColor(ParticleColor.GRAY);
        // keep box visible
    }

    public void cancelBoxSelection() {
        if (selectionBox == null) {
            return;
        }
        selectionBoxEditing = false;
        selectionBox = null;
        session.removeParticle(selectionBoxParticle);
        selectionBoxMembers.clear();
    }

    public void clearGroupSelection() {
        groupMembers.clear();
    }

    public void addToGroup(ElementDiscoveryEntry discoveryEntry, SelectableElement element) {
        groupMembers.put(discoveryEntry, element);
    }

    public void removeFromGroup(ElementDiscoveryEntry discoveryEntry, SelectableElement element) {
        groupMembers.remove(discoveryEntry, element);
    }

    private BoundingBox getDiscoveryBox(EyeRay eyeRay) {
        Vector3dc origin = eyeRay.origin();
        Vector3dc halfSize = new Vector3d(range);
        return BoundingBox.of(
                origin.sub(halfSize, new Vector3d()),
                origin.add(halfSize, new Vector3d()));
    }

    @Override
    public void onUpdate(UpdateContext context) {
        if (selectionBoxEditing) {
            Vector3dc cursor = context.eyeRay().point(selectionCursorOffset);
            selectionBox = BoundingBox.of(selectionBoxOrigin, cursor);
            Vector3d size = selectionBox.getSize(new Vector3d());
            double maxSize = size.get(size.maxComponent());
            if (maxSize > boxSizeLimit) {
                cancelBoxSelection();
            }
        }

        EyeRay eyeRay = context.eyeRay();
        BoundingBox box = getDiscoveryBox(eyeRay);

        Set<ElementDiscoveryEntry> foundEntries = new HashSet<>(groupMembers.keySet());
        for (ElementDiscoverySource source : sources) {
            source.discover(eyeRay.world(), box, foundEntries::add);
        }

        // Process removed entries
        for (Iterator<Map.Entry<ElementDiscoveryEntry, ElementEntry>> iterator = entries.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<ElementDiscoveryEntry, ElementEntry> entry = iterator.next();
            if (!foundEntries.contains(entry.getKey())) {
                ElementButton button = entry.getValue().button;
                if (button != null) {
                    removeButton(button);
                    buttonCount--;
                }
                iterator.remove();
            }
        }

        // Process added entries
        for (ElementDiscoveryEntry entry : foundEntries) {
            if (isButtonLimitReached()) {
                break;
            }
            entries.computeIfAbsent(entry, this::addEntry);
        }

        if (selectionBox != null) {
            selectionBoxParticle.setBoundingBox(selectionBox);
            selectionBoxMembers.values().removeIf(e -> !e.isValid() || !e.getBoundingBox().overlaps(selectionBox));
            for (ElementEntry entry : entries.values()) {
                ElementButton button = entry.button;
                if (button != null) {
                    SelectableElement element = button.element;
                    if (element.getBoundingBox().overlaps(selectionBox)) {
                        if (groupMembers.size() + selectionBoxMembers.size() < groupLimit) {
                            selectionBoxMembers.putIfAbsent(button.entry, element);
                        } else {
                            break;
                        }
                        groupMembers.remove(button.entry);
                    }
                }
            }
            if (!selectionBoxEditing) {
                // already confirmed the box, add contained entities to the group
                groupMembers.putAll(selectionBoxMembers);
                selectionBoxMembers.clear();
            }
        }

        groupMembers.values().removeIf(e -> !e.isValid());

        if (selectionBoxEditing) {
            context.addInput(new ConfirmBoxSelectionInput(this));
        }
        if (!groupMembers.isEmpty()) {
            context.addInput(new SelectGroupInput(session, groupMembers.values()));
        }

        super.onUpdate(context);

        if (selectionBox != null) {
            context.addInput(new CancelBoxSelectionInput(this));
        }
        if (!groupMembers.isEmpty()) {
            context.addInput(new ClearGroupSelectionInput(this));
        }
        if (groupMembers.isEmpty() && selectionBox == null && allowSpawn) {
            context.addInput(openSpawnMenuInput);
        }
        if (selectionBox == null && allowGroups) {
            context.addInput(new StartBoxSelectionInput(this));
        }

        int groupSize = groupMembers.size() + selectionBoxMembers.size();
        if (groupSize == 0) {
            if (isButtonLimitReached()) {
                context.setActionBar(Message.error("easyarmorstands.node.select-entity.limit-reached"));
            } else {
                context.setActionBar(name);
            }
        } else if (groupSize == 1) {
            context.setActionBar(Message.component("easyarmorstands.node.group-selected.single"));
        } else {
            Component status = Message.component("easyarmorstands.node.group-selected", Component.text(groupSize));
            if (groupSize >= groupLimit) {
                status = Message.format(MessageStyle.ERROR, status);
            }
            context.setActionBar(status);
        }
    }

    private boolean isButtonLimitReached() {
        return buttonLimit > 0 && buttonCount >= buttonLimit;
    }

    private ElementEntry addEntry(ElementDiscoveryEntry entry) {
        SelectableElement element = entry.getElement();
        if (element == null || !platform.canDiscoverElement(session.player(), element)) {
            return ElementEntry.EMPTY;
        }
        ElementButton button = new ElementButton(entry, session, element);
        addButton(button);
        buttonCount++;
        return new ElementEntry(button);
    }

    @Override
    public void onExit(ExitContext context) {
        super.onExit(context);
        for (ElementEntry entry : entries.values()) {
            if (entry.button != null) {
                removeButton(entry.button);
            }
        }
        entries.clear();
        buttonCount = 0;
        cancelBoxSelection();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean selectElement(SelectableElement element) {
        if (!platform.canSelectElement(session.player(), element)) {
            return false;
        }

        Node node = element.createNode(session);
        session.returnToNode(this);
        session.pushNode(node);
        return true;
    }

    @Override
    public boolean selectElements(List<? extends SelectableElement> elements) {
        if (elements.size() == 1) {
            return selectElement(elements.get(0));
        }

        Group group = new Group(session);
        for (SelectableElement element : elements) {
            if (platform.canSelectElement(session.player(), element)) {
                group.addMember(element);
            }
        }
        if (!group.isValid()) {
            return false;
        }

        session.returnToNode(this);
        session.pushNode(new GroupRootNode(group));
        return true;
    }

    private static class ElementEntry {
        private static final ElementEntry EMPTY = new ElementEntry(null);

        private final @Nullable ElementButton button;

        private ElementEntry(@Nullable ElementButton button) {
            this.button = button;
        }
    }

    private class ElementButton implements MenuButton {
        private final ElementDiscoveryEntry entry;
        private final SelectableElement element;
        private final Button button;
        private final SelectElementInput selectInput;
        private final AddElementToGroupInput addToGroupInput;
        private final RemoveElementFromGroupInput removeFromGroupInput;

        private ElementButton(ElementDiscoveryEntry entry, Session session, SelectableElement element) {
            this.entry = entry;
            this.element = element;
            this.button = element.createButton(session);
            this.selectInput = new SelectElementInput(platform, session, element);
            this.addToGroupInput = new AddElementToGroupInput(ElementSelectionNodeImpl.this, entry, element);
            this.removeFromGroupInput = new RemoveElementFromGroupInput(ElementSelectionNodeImpl.this, entry, element);
        }

        @Override
        public Button getButton() {
            return button;
        }

        @Override
        public void onUpdate(Session session, @Nullable Vector3dc cursor, @NotNull UpdateContext context) {
            context.addInput(selectInput);
            if (allowGroups && selectionBox == null) {
                if (!groupMembers.containsKey(entry)) {
                    if (groupMembers.size() < groupLimit) {
                        context.addInput(addToGroupInput);
                    }
                } else {
                    context.addInput(removeFromGroupInput);
                }
            }
        }

        @Override
        public Component getName() {
            return element.getName();
        }

        @Override
        public boolean isAlwaysFocused() {
            return groupMembers.containsKey(entry) || selectionBoxMembers.containsKey(entry);
        }
    }
}
