package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.node.ClickableNode;
import me.m56738.easyarmorstands.node.ParentNode;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WorldSession extends Session {
    private final ParentNode rootNode = new ParentNode(this, Component.text("Select an entity"));
    private final Map<Entity, ClickableNode> nodes = new HashMap<>();
    private final List<EntityNodeProvider> providers = new ArrayList<>();

    public WorldSession(Player player) {
        super(player);
        rootNode.setRoot(true);
        pushNode(rootNode);
    }

    public void addProvider(EntityNodeProvider provider) {
        providers.add(provider);
    }

    @Override
    public boolean update() {
        Set<Entity> removed = new HashSet<>(nodes.keySet());

        for (Entity entity : getPlayer().getWorld().getNearbyEntities(getPlayer().getLocation(), 32, 32, 32)) {
            // entity exists, wasn't removed
            if (removed.remove(entity)) {
                continue;
            }

            // entity is new, create a node for it
            for (EntityNodeProvider provider : providers) {
                ClickableNode node = provider.createNode(this, entity);
                if (node != null) {
                    nodes.put(entity, node);
                    rootNode.addNode(node);
                    break;
                }
            }
        }

        // remove nodes of entities which no longer exist
        for (Entity entity : removed) {
            rootNode.removeNode(nodes.remove(entity));
        }

        return super.update();
    }
}
