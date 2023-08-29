package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.MenuNode;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import me.m56738.easyarmorstands.api.event.session.SessionSelectElementEvent;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.element.EntityElementProviderRegistryImpl;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.permission.Permissions;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EntitySelectionNode extends MenuNode {
    private final Session session;
    private final EntityElementProviderRegistryImpl providerRegistry;
    private final Map<Entity, ElementButton> buttons = new HashMap<>();
    private final Component name;

    public EntitySelectionNode(Session session, EntityElementProviderRegistryImpl providerRegistry) {
        super(session);
        this.session = session;
        this.providerRegistry = providerRegistry;
        this.name = Message.component("easyarmorstands.node.select-entity");
    }

    @Override
    public void onUpdate(UpdateContext context) {
        Set<Entity> notSeen = new HashSet<>(buttons.keySet());
        double range = context.eyeRay().length();
        Player player = session.player();
        Location location = player.getLocation();
        for (Entity entity : location.getWorld().getNearbyEntities(location, range, range, range)) {
            if (entity.getLocation().distanceSquared(location) > range * range) {
                continue;
            }

            if (notSeen.remove(entity)) {
                // entity already existed
                continue;
            }

            // entity is new, create a button for it
            Element element = providerRegistry.getElement(entity);
            ElementButton button = null;
            if (element instanceof SelectableElement) {
                SelectableElement selectableElement = (SelectableElement) element;
                if (selectableElement.canEdit(session.player())) {
                    button = new ElementButton(session, selectableElement);
                    addButton(button);
                }
            }

            buttons.put(entity, button);
        }

        // remove buttons of entities which no longer exist
        for (Entity entity : notSeen) {
            removeButton(buttons.remove(entity));
        }

        super.onUpdate(context);

        session.setActionBar(name);
    }

    @Override
    public void onExit(ExitContext context) {
        super.onExit(context);
        for (ElementButton button : buttons.values()) {
            removeButton(button);
        }
        buttons.clear();
    }

    @Override
    public boolean onClick(ClickContext context) {
        if (super.onClick(context)) {
            return true;
        }

        if (context.type() == ClickContext.Type.RIGHT_CLICK) {
            Entity entity = context.entity();
            if (entity != null) {
                ElementButton button = buttons.get(entity);
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
        public Button getButton() {
            return button;
        }

        @Override
        public void onClick(Session session, @Nullable Vector3dc cursor) {
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
        public Component getName() {
            return element.getName();
        }
    }
}
