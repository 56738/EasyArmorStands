package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.editor.EditableObject;
import me.m56738.easyarmorstands.editor.EntityObjectProviderRegistry;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.joml.Vector3dc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EntitySelectionNode extends MenuNode {
    private final Session session;
    private final EntityObjectProviderRegistry providerRegistry;
    private final Map<Entity, EditableObjectButton> buttons = new HashMap<>();

    public EntitySelectionNode(Session session, Component name, EntityObjectProviderRegistry providerRegistry) {
        super(session, name);
        this.session = session;
        this.providerRegistry = providerRegistry;
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        Set<Entity> removed = new HashSet<>(buttons.keySet());
        double range = session.getRange();
        Player player = session.getPlayer();
        Location location = player.getLocation();
        for (Entity entity : location.getWorld().getNearbyEntities(location, range, range, range)) {
            if (entity.getLocation().distanceSquared(location) > range * range) {
                continue;
            }

            // entity exists, wasn't removed
            if (removed.remove(entity)) {
                continue;
            }

            // entity is new, create a button for it
            EditableObject editableObject = providerRegistry.createEditableObject(entity);
            EditableObjectButton button = null;
            if (editableObject != null) {
                button = new EditableObjectButton(session, editableObject);
                addButton(button);
            }

            buttons.put(entity, button);
        }

        // remove buttons of entities which no longer exist
        for (Entity entity : removed) {
            removeButton(buttons.remove(entity));
        }

        super.onUpdate(eyes, target);
    }

    @Override
    public boolean onClick(Vector3dc eyes, Vector3dc target, ClickContext context) {
        if (super.onClick(eyes, target, context)) {
            return true;
        }

        if (context.getType() == ClickType.RIGHT_CLICK) {
            Entity entity = context.getEntity();
            if (entity != null) {
                EditableObjectButton button = buttons.get(entity);
                if (button != null) {
                    button.onClick(session);
                    return true;
                }
            }

            Player player = session.getPlayer();
            if (player.isSneaking() && player.hasPermission("easyarmorstands.spawn")) {
                Session.openSpawnMenu(player);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public boolean selectEntity(Entity entity) {
        EditableObject editableObject = providerRegistry.createEditableObject(entity);
        if (editableObject != null) {
            Node node = editableObject.createNode(session);
            if (node != null) {
                session.clearNode();
                session.pushNode(this);
                session.pushNode(node);
                return true;
            }
        }
        return false;
    }

    private static class EditableObjectButton implements MenuButton {
        private final Session session;
        private final EditableObject editableObject;

        private EditableObjectButton(Session session, EditableObject editableObject) {
            this.session = session;
            this.editableObject = editableObject;
        }

        @Override
        public Button createButton() {
            return editableObject.createButton(session);
        }

        @Override
        public void onClick(Session session) {
            EditableObjectNode node = editableObject.createNode(session);
            if (node != null) {
                session.pushNode(node);
            }
        }
    }
}
