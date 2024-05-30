package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode;
import me.m56738.easyarmorstands.api.editor.node.MenuNode;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.ElementDiscoverySource;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.particle.BoundingBoxParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.group.Group;
import me.m56738.easyarmorstands.group.node.GroupRootNode;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.message.MessageStyle;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

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
    private BoundingBox selectionBox;
    private boolean selectionBoxEditing;
    private double range = EasyArmorStandsPlugin.getInstance().getConfiguration().editorSelectionRange;
    private double boxSizeLimit = EasyArmorStandsPlugin.getInstance().getConfiguration().editorSelectionDistance;
    private int groupLimit = EasyArmorStandsPlugin.getInstance().getConfiguration().editorSelectionLimit;

    public ElementSelectionNodeImpl(Session session) {
        super(session);
        this.session = session;
        this.name = Message.component("easyarmorstands.node.select-entity");
        this.selectionBoxParticle = session.particleProvider().createAxisAlignedBox();
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

    private void startBoxSelection(Vector3dc origin) {
        selectionBoxOrigin.set(origin);
        if (selectionBox != null) {
            session.removeParticle(selectionBoxParticle);
        }
        selectionBox = BoundingBox.of(selectionBoxOrigin);
        selectionBoxParticle.setBoundingBox(selectionBox);
        selectionBoxParticle.setColor(ParticleColor.YELLOW);
        selectionBoxEditing = true;
        session.addParticle(selectionBoxParticle);
    }

    private void finishBoxSelection() {
        selectionBoxEditing = false;
        groupMembers.putAll(selectionBoxMembers);
        selectionBoxMembers.clear();
        selectionBoxParticle.setColor(ParticleColor.GRAY);
        // keep box visible
    }

    private void cancelBoxSelection() {
        if (selectionBox == null) {
            return;
        }
        selectionBoxEditing = false;
        selectionBox = null;
        session.removeParticle(selectionBoxParticle);
        selectionBoxMembers.clear();
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
                ElementButton button = entry.getValue().button;
                if (button != null) {
                    removeButton(button);
                }
                iterator.remove();
            }
        }

        // Process added entries
        for (ElementDiscoveryEntry entry : foundEntries) {
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

        super.onUpdate(context);

        groupMembers.values().removeIf(e -> !e.isValid());

        int groupSize = groupMembers.size() + selectionBoxMembers.size();
        if (groupSize == 0) {
            context.setActionBar(name);
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

    private SelectableElement getElement(ElementDiscoveryEntry entry) {
        ElementEntry elementEntry = entries.get(entry);
        if (elementEntry == null || elementEntry.button == null) {
            return entry.getElement();
        }
        return elementEntry.button.element;
    }

    private ElementEntry addEntry(ElementDiscoveryEntry entry) {
        SelectableElement element = entry.getElement();
        ChangeContext context = new EasPlayer(session.player());
        if (element == null || !context.canEditElement(element)) {
            return ElementEntry.EMPTY;
        }
        ElementButton button = new ElementButton(entry, session, element);
        addButton(button);
        return new ElementEntry(button);
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
        cancelBoxSelection();
    }

    private Consumer<Vector3dc> getEntityClickHandler(Entity entity) {
        for (ElementDiscoverySource source : sources) {
            if (source instanceof EntityElementDiscoverySource) {
                ElementDiscoveryEntry entry = ((EntityElementDiscoverySource) source).getEntry(entity);
                SelectableElement element = getElement(entry);
                if (element != null) {
                    return cursor -> onClickElement(entry, element, cursor);
                }
            }
        }
        return null;
    }

    @Override
    public boolean onClick(@NotNull ClickContext context) {
        Player player = session.player();
        if (context.type() == ClickContext.Type.RIGHT_CLICK && selectionBoxEditing) {
            finishBoxSelection();
            return true;
        }

        if (context.type() == ClickContext.Type.RIGHT_CLICK && !player.isSneaking()) {
            if (!groupMembers.isEmpty()) {
                // finish group selection
                int groupSize = groupMembers.size();
                if (groupSize > 1) {
                    Group group = new Group(session);
                    for (SelectableElement element : groupMembers.values()) {
                        group.addMember(element);
                    }
                    session.pushNode(new GroupRootNode(group));
                } else {
                    SelectableElement element = groupMembers.values().iterator().next();
                    session.pushNode(element.createNode(session));
                }
                return true;
            }
        }

        if (context.type() == ClickContext.Type.LEFT_CLICK) {
            if (selectionBox != null) {
                cancelBoxSelection();
                return true;
            }

            if (!groupMembers.isEmpty()) {
                // cancel group selection
                groupMembers.clear();
                return true;
            }

            if (player.hasPermission(Permissions.SPAWN)) {
                // open spawn menu
                Menu menu = EasyArmorStandsPlugin.getInstance().createSpawnMenu(player);
                player.openInventory(menu.getInventory());
                return true;
            }
        }

        if (super.onClick(context)) {
            return true;
        }

        if (context.type() == ClickContext.Type.RIGHT_CLICK) {
            Entity entity = context.entity();
            if (entity != null) {
                Consumer<Vector3dc> entityClickHandler = getEntityClickHandler(entity);
                if (entityClickHandler != null) {
                    entityClickHandler.accept(null);
                    return true;
                }
            }

            if (player.isSneaking() && !selectionBoxEditing && player.hasPermission(Permissions.GROUP)) {
                // start box selection
                startBoxSelection(context.eyeRay().point(selectionCursorOffset));
                return true;
            }
        }

        return false;
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

    private void onClickElement(ElementDiscoveryEntry entry, SelectableElement element, @Nullable Vector3dc cursor) {
        if (groupMembers.remove(entry) != null) {
            return;
        }

        ChangeContext context = new EasPlayer(session.player());
        if (!context.canEditElement(element)) {
            return;
        }

        if (session.player().isSneaking() && session.player().hasPermission(Permissions.GROUP)) {
            if (groupMembers.size() < groupLimit) {
                groupMembers.put(entry, element);
            }
            return;
        }

        Node node = element.createNode(session);
        session.pushNode(node, cursor);
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

        private ElementButton(ElementDiscoveryEntry entry, Session session, SelectableElement element) {
            this.entry = entry;
            this.element = element;
            this.button = element.createButton(session);
        }

        @Override
        public @NotNull Button getButton() {
            return button;
        }

        @Override
        public void onClick(@NotNull Session session, @Nullable Vector3dc cursor) {
            onClickElement(entry, element, cursor);
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
