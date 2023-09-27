package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.MenuNode;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.ElementDiscoverySource;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.group.Group;
import me.m56738.easyarmorstands.group.node.GroupRootNode;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ElementSelectionNode extends MenuNode {
    private final Session session;
    private final Map<ElementDiscoveryEntry, ElementEntry> entries = new HashMap<>();
    private final Component name;
    private final Set<ElementDiscoverySource> sources = new LinkedHashSet<>();
    private final Map<ElementDiscoveryEntry, SelectableElement> groupMembers = new LinkedHashMap<>();

    public ElementSelectionNode(Session session) {
        super(session);
        this.session = session;
        this.name = Message.component("easyarmorstands.node.select-entity");
    }

    public void addSource(ElementDiscoverySource source) {
        sources.add(source);
    }

    private BoundingBox getDiscoveryBox(EyeRay eyeRay) {
        Vector3dc origin = eyeRay.origin();
        Vector3dc size = new Vector3d(eyeRay.length());
        return BoundingBox.of(
                origin.sub(size, new Vector3d()),
                origin.add(size, new Vector3d()));
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
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

        super.onUpdate(context);

        groupMembers.values().removeIf(e -> !e.isValid());

        int groupSize = groupMembers.size();
        if (groupSize == 0) {
            context.setActionBar(name);
        } else if (groupSize == 1) {
            context.setActionBar(Message.component("easyarmorstands.node.group-selected.single"));
        } else {
            context.setActionBar(Message.component("easyarmorstands.node.group-selected", Component.text(groupSize)));
        }
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
        groupMembers.clear();
    }

    private ElementButton findButton(Entity entity) {
        for (ElementDiscoverySource source : sources) {
            if (source instanceof EntityElementDiscoverySource) {
                ElementEntry entry = entries.get(((EntityElementDiscoverySource) source).getEntry(entity));
                if (entry.button != null) {
                    return entry.button;
                }
            }
        }
        return null;
    }

    @Override
    public boolean onClick(@NotNull ClickContext context) {
        if (context.type() == ClickContext.Type.RIGHT_CLICK && !groupMembers.isEmpty() && !session.player().isSneaking()) {
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
            groupMembers.clear();
            return true;
        }

        if (context.type() == ClickContext.Type.LEFT_CLICK && !groupMembers.isEmpty()) {
            groupMembers.clear();
            return true;
        }

        if (context.type() == ClickContext.Type.LEFT_CLICK) {
            Player player = session.player();
            if (player.hasPermission(Permissions.SPAWN)) {
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
                ElementButton button = findButton(entity);
                if (button != null) {
                    button.onClick(session, null);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public boolean selectElement(Element element) {
        if (!(element instanceof SelectableElement)) {
            return false;
        }

        SelectableElement selectableElement = (SelectableElement) element;
        ChangeContext context = new EasPlayer(session.player());
        if (!context.canEditElement(selectableElement)) {
            return false;
        }

        Node node = selectableElement.createNode(session);
        session.returnToNode(this);
        session.pushNode(node);
        return true;
    }

    public boolean selectElements(List<? extends SelectableElement> elements) {
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
            if (groupMembers.remove(entry) != null) {
                return;
            }

            ChangeContext context = new EasPlayer(session.player());
            if (!context.canEditElement(element)) {
                return;
            }

            if (session.player().isSneaking() && session.player().hasPermission(Permissions.GROUP)) {
                groupMembers.put(entry, element);
                return;
            }

            Node node = element.createNode(session);
            session.pushNode(node, cursor);
        }

        @Override
        public @NotNull Component getName() {
            return element.getName();
        }

        @Override
        public boolean isHighlighted() {
            return groupMembers.containsKey(entry);
        }
    }
}
