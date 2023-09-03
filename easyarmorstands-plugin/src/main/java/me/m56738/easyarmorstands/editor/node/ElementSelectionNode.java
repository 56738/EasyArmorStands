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
import me.m56738.easyarmorstands.api.event.session.SessionSelectElementEvent;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.group.Group;
import me.m56738.easyarmorstands.group.node.GroupRootNode;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
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
import java.util.Map;
import java.util.Set;

public class ElementSelectionNode extends MenuNode {
    private final Session session;
    private final Map<ElementDiscoveryEntry, ElementButton> buttons = new HashMap<>();
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
        for (Iterator<Map.Entry<ElementDiscoveryEntry, ElementButton>> iterator = buttons.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<ElementDiscoveryEntry, ElementButton> entry = iterator.next();
            if (!foundEntries.contains(entry.getKey())) {
                removeButton(entry.getValue());
                iterator.remove();
            }
        }

        // Process added entries
        for (ElementDiscoveryEntry entry : foundEntries) {
            buttons.computeIfAbsent(entry, this::addEntry);
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

    private ElementButton addEntry(ElementDiscoveryEntry entry) {
        SelectableElement element = entry.getElement();
        if (element == null || !element.canEdit(session.player())) {
            return null;
        }
        ElementButton button = new ElementButton(entry, session, element);
        addButton(button);
        return button;
    }

    @Override
    public void onExit(@NotNull ExitContext context) {
        super.onExit(context);
        for (ElementButton button : buttons.values()) {
            removeButton(button);
        }
        buttons.clear();
        groupMembers.clear();
    }

    private ElementButton findButton(Entity entity) {
        for (ElementDiscoverySource source : sources) {
            if (source instanceof EntityElementDiscoverySource) {
                ElementButton button = buttons.get(((EntityElementDiscoverySource) source).getEntry(entity));
                if (button != null) {
                    return button;
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
                Node node = element.createNode(session);
                if (node != null) {
                    session.pushNode(node);
                }
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

        if (!((SelectableElement) element).canEdit(session.player())) {
            return false;
        }

        SessionSelectElementEvent event = new SessionSelectElementEvent(session, element);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }

        Node node = ((SelectableElement) element).createNode(session);
        if (node == null) {
            return false;
        }

        session.returnToNode(this);
        session.pushNode(node);
        return true;

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

            SessionSelectElementEvent event = new SessionSelectElementEvent(session, element);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }

            if (session.player().isSneaking() && session.player().hasPermission(Permissions.GROUP)) {
                groupMembers.put(entry, element);
                return;
            }

            Node node = element.createNode(session);
            if (node != null) {
                session.pushNode(node, cursor);
            }
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
