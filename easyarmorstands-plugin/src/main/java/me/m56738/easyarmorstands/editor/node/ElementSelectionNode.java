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
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ElementSelectionNode extends MenuNode {
    private final Session session;
    private final Map<ElementDiscoveryEntry, ElementButton> buttons = new HashMap<>();
    private final Component name;
    private final Set<ElementDiscoverySource> sources = new LinkedHashSet<>();

    public ElementSelectionNode(Session session) {
        super(session);
        this.session = session;
        this.name = Message.component("easyarmorstands.node.select-entity");
    }

    public void addSource(ElementDiscoverySource source) {
        sources.add(source);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        EyeRay eyeRay = context.eyeRay();

        Set<ElementDiscoveryEntry> foundEntries = new HashSet<>();
        for (ElementDiscoverySource source : sources) {
            source.discover(eyeRay, foundEntries::add);
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

        context.setActionBar(name);
    }

    private ElementButton addEntry(ElementDiscoveryEntry entry) {
        SelectableElement element = entry.getElement();
        if (element == null || !element.canEdit(session.player())) {
            return null;
        }
        ElementButton button = new ElementButton(session, element);
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

            Player player = session.player();
            if (player.isSneaking() && player.hasPermission(Permissions.SPAWN)) {
                Menu menu = EasyArmorStandsPlugin.getInstance().createSpawnMenu(session.player());
                player.openInventory(menu.getInventory());
                return true;
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

        session.clearNode();
        session.pushNode(this);
        session.pushNode(node);
        return true;

    }

    private static class ElementButton implements MenuButton {
        private final SelectableElement element;
        private final Button button;

        private ElementButton(Session session, SelectableElement element) {
            this.element = element;
            this.button = element.createButton(session);
        }

        @Override
        public @NotNull Button getButton() {
            return button;
        }

        @Override
        public void onClick(@NotNull Session session, @Nullable Vector3dc cursor) {
            if (!element.canEdit(session.player())) {
                return;
            }

            SessionSelectElementEvent event = new SessionSelectElementEvent(session, element);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
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
    }
}
