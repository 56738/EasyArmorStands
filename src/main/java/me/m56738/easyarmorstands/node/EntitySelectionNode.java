package me.m56738.easyarmorstands.node;

import me.m56738.easyarmorstands.session.EntityNodeProvider;
import me.m56738.easyarmorstands.session.Session;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.joml.Vector3dc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EntitySelectionNode extends ParentNode {
    private final Session session;
    private final Map<Entity, ClickableNode> nodes = new HashMap<>();
    private final List<EntityNodeProvider> providers = new ArrayList<>();

    public EntitySelectionNode(Session session, Component name) {
        super(session, name);
        this.session = session;
    }

    public void addProvider(EntityNodeProvider provider) {
        providers.add(provider);
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        Set<Entity> removed = new HashSet<>(nodes.keySet());
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

            // entity is new, create a node for it
            for (EntityNodeProvider provider : providers) {
                EntityNode node = provider.createNode(session, entity);
                if (node != null) {
                    nodes.put(entity, node);
                    addNode(node);
                    break;
                }
            }
        }

        // remove nodes of entities which no longer exist
        for (Entity entity : removed) {
            removeNode(nodes.remove(entity));
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
                ClickableNode node = nodes.get(entity);
                if (node != null) {
                    session.pushNode(node);
                    return true;
                }
            }
        }

        return false;
    }
}
