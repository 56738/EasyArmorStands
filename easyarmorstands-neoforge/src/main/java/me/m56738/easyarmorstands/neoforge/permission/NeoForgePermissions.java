package me.m56738.easyarmorstands.neoforge.permission;

import me.m56738.easyarmorstands.common.permission.Permission;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.server.permission.PermissionAPI;
import net.neoforged.neoforge.server.permission.nodes.PermissionDynamicContext;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import net.neoforged.neoforge.server.permission.nodes.PermissionTypes;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NeoForgePermissions {
    private final Map<String, PermissionNode<Boolean>> nodes = new HashMap<>();
    private final Map<String, Map<PermissionNode<Boolean>, Boolean>> parents = new HashMap<>();

    public @Nullable PermissionNode<Boolean> getNode(String name) {
        return nodes.get(name);
    }

    public Map<PermissionNode<Boolean>, Boolean> getParents(String name) {
        return parents.getOrDefault(name, Map.of());
    }

    public Collection<PermissionNode<Boolean>> getNodes() {
        return nodes.values();
    }

    public void registerAll(Set<Permission> permissions) {
        for (Permission permission : permissions) {
            register(permission);
        }
    }

    public void register(Permission permission) {
        String name = permission.name();
        int separator = name.indexOf('.');
        register(new PermissionNode<>(
                        name.substring(0, separator),
                        name.substring(separator + 1),
                        PermissionTypes.BOOLEAN,
                        new DefaultResolver(permission.name())),
                permission.children());
    }

    public void register(PermissionNode<Boolean> permission, Map<String, Boolean> children) {
        this.nodes.put(permission.getNodeName(), permission);
        for (Map.Entry<String, Boolean> entry : children.entrySet()) {
            this.parents.computeIfAbsent(entry.getKey(), n -> new HashMap<>()).put(permission, entry.getValue());
        }
    }

    private class DefaultResolver implements PermissionNode.PermissionResolver<Boolean> {
        private final String name;

        private DefaultResolver(String name) {
            this.name = name;
        }

        @Override
        public Boolean resolve(@Nullable ServerPlayer player, UUID playerUUID, PermissionDynamicContext<?>... context) {
            if (player != null) {
                MinecraftServer server = player.getServer();
                if (server != null) {
                    if (player.hasPermissions(server.getOperatorUserPermissionLevel())) {
                        return true;
                    }
                }
            }
            for (Map.Entry<PermissionNode<Boolean>, Boolean> entry : getParents(name).entrySet()) {
                PermissionNode<Boolean> parent = entry.getKey();
                boolean value;
                if (player != null) {
                    value = PermissionAPI.getPermission(player, parent);
                } else {
                    value = PermissionAPI.getOfflinePermission(playerUUID, parent);
                }
                if (value) {
                    return entry.getValue();
                }
            }
            return false;
        }
    }
}
