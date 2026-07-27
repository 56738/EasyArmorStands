package me.m56738.easyarmorstands.platform.neoforge;

import net.neoforged.neoforge.server.permission.nodes.PermissionNode;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NeoForgePermissionManager {
    private final Map<String, PermissionNode<Boolean>> nodes = new HashMap<>();

    public void register(PermissionNode<Boolean> node) {
        nodes.put(node.getNodeName(), node);
    }

    public PermissionNode<Boolean> get(String name) {
        PermissionNode<Boolean> node = nodes.get(name);
        if (node == null) {
            throw new IllegalArgumentException("Permission not registered: " + name);
        }
        return node;
    }

    public Collection<PermissionNode<?>> getAllNodes() {
        return Collections.unmodifiableCollection(nodes.values());
    }
}
