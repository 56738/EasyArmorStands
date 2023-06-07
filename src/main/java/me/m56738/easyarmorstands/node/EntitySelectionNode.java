package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.session.EntityButtonPriority;
import me.m56738.easyarmorstands.session.EntityButtonProvider;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EntitySelectionNode extends MenuNode {
    private final Session session;
    private final Map<Entity, Button> buttons = new HashMap<>();
    private final EnumMap<EntityButtonPriority, List<EntityButtonProvider>> providers = new EnumMap<>(EntityButtonPriority.class);

    public EntitySelectionNode(Session session, Component name) {
        super(session, name);
        this.session = session;
        for (EntityButtonPriority priority : EntityButtonPriority.values()) {
            this.providers.put(priority, new ArrayList<>());
        }
    }

    public void addProvider(EntityButtonProvider provider) {
        providers.get(provider.getPriority()).add(provider);
    }

    private @Nullable Button createButton(Entity entity) {
        if (!entity.isValid()) {
            return null;
        }
        for (EntityButtonPriority priority : EntityButtonPriority.values()) {
            for (EntityButtonProvider provider : providers.get(priority)) {
                Button button = provider.createButton(session, entity);
                if (button != null) {
                    return button;
                }
            }
        }
        return null;
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
            Button button = createButton(entity);
            if (button != null) {
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
                Button button = buttons.get(entity);
                if (button != null) {
                    Node node = button.createNode();
                    if (node != null) {
                        session.pushNode(node);
                    }
                    return true;
                }
            }

            if (session.getPlayer().isSneaking() && session.getPlayer().hasPermission("easyarmorstands.spawn")) {
                session.openSpawnMenu();
                return true;
            }
        }

        return false;
    }

    public void selectEntity(Entity entity) {
        Button button = createButton(entity);
        if (button != null) {
            Node node = button.createNode();
            if (node != null) {
                session.pushNode(node);
            }
        }
    }
}
