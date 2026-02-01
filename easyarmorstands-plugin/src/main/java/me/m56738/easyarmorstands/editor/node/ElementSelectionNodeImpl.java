package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode;
import me.m56738.easyarmorstands.api.editor.node.MenuNode;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.ElementDiscoverySource;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.particle.BoundingBoxParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.editor.input.OpenSpawnMenuInput;
import me.m56738.easyarmorstands.editor.input.selection.AddElementToGroupInput;
import me.m56738.easyarmorstands.editor.input.selection.ClearGroupSelectionInput;
import me.m56738.easyarmorstands.editor.input.selection.RemoveElementFromGroupInput;
import me.m56738.easyarmorstands.editor.input.selection.SelectElementInput;
import me.m56738.easyarmorstands.editor.input.selection.SelectGroupInput;
import me.m56738.easyarmorstands.editor.input.selection.box.CancelBoxSelectionInput;
import me.m56738.easyarmorstands.editor.input.selection.box.ConfirmBoxSelectionInput;
import me.m56738.easyarmorstands.editor.input.selection.box.StartBoxSelectionInput;
import me.m56738.easyarmorstands.group.Group;
import me.m56738.easyarmorstands.group.node.GroupRootNode;
import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.message.MessageStyle;
import me.m56738.easyarmorstands.permission.Permissions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ElementSelectionNodeImpl extends MenuNode implements ElementSelectionNode {
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
    private BoundingBox selectionBox;
    private boolean selectionBoxEditing;
    private double range = EasyArmorStandsPlugin.getInstance().getConfiguration().editor.selection.range;
    private double boxSizeLimit = EasyArmorStandsPlugin.getInstance().getConfiguration().editor.selection.group.range;
    private int buttonLimit = EasyArmorStandsPlugin.getInstance().getConfiguration().editor.discovery.limit;
    private int groupLimit = EasyArmorStandsPlugin.getInstance().getConfiguration().editor.selection.group.limit;
    private int buttonCount;

    public ElementSelectionNodeImpl(Session session) {
        super(session);
        this.session = session;
        this.name = Message.component("easyarmorstands.node.select-entity");
        this.selectionBoxParticle = session.particleProvider().createAxisAlignedBox();
        this.openSpawnMenuInput = new OpenSpawnMenuInput(session);
        this.allowGroups = session.player().hasPermission(Permissions.GROUP);
        this.allowSpawn = session.player().hasPermission(Permissions.SPAWN);
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
    public void addSource(@NotNull ElementDiscoverySource source) {
        sources.add(source);
    }

    @Override
    public void removeSource(@NotNull ElementDiscoverySource source) {
        sources.remove(source);
    }

    @Override
    public @Unmodifiable @NotNull Iterable<ElementDiscoverySource> getSources() {
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
    public void onUpdate(@NotNull UpdateContext context) {
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
                removeEntry(entry.getValue());
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

    @Override
    public void updateEntry(@NotNull ElementDiscoveryEntry entry) {
        entries.computeIfPresent(entry, (e, elementEntry) -> {
            removeEntry(elementEntry);
            return addEntry(e);
        });
    }

    private ElementEntry addEntry(ElementDiscoveryEntry entry) {
        SelectableElement element = entry.getElement();
        ChangeContext context = new EasPlayer(session.player());
        if (element == null || !context.canDiscoverElement(element)) {
            return ElementEntry.EMPTY;
        }
        ElementButton button = new ElementButton(entry, session, element);
        addButton(button);
        buttonCount++;
        return new ElementEntry(button);
    }

    private void removeEntry(ElementEntry entry) {
        ElementButton button = entry.button;
        if (button != null) {
            removeButton(button);
            buttonCount--;
        }
    }

    @Override
    public void onExit(@NotNull ExitContext context) {
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
    public boolean selectElement(@NotNull SelectableElement element) {
        ChangeContext context = new EasPlayer(session.player());
        if (!context.canEditElement(element)) {
            return false;
        }

        Node node = element.createNode(session);
        session.returnToNode(this);
        session.pushNode(node);
        return true;
    }

    @Override
    public boolean selectElements(@NotNull List<? extends SelectableElement> elements) {
        if (elements.size() == 1) {
            return selectElement(elements.get(0));
        }

        Group group = new Group(session);
        ChangeContext context = new EasPlayer(session.player());
        for (SelectableElement element : elements) {
            if (context.canEditElement(element)) {
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
            this.selectInput = new SelectElementInput(session, element);
            this.addToGroupInput = new AddElementToGroupInput(ElementSelectionNodeImpl.this, entry, element);
            this.removeFromGroupInput = new RemoveElementFromGroupInput(ElementSelectionNodeImpl.this, entry, element);
        }

        @Override
        public @NotNull Button getButton() {
            return button;
        }

        @Override
        public void onUpdate(@NotNull Session session, @Nullable Vector3dc cursor, @NotNull UpdateContext context) {
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
        public @NotNull Component getName() {
            return element.getName();
        }

        @Override
        public boolean isAlwaysFocused() {
            return groupMembers.containsKey(entry) || selectionBoxMembers.containsKey(entry);
        }
    }
}
