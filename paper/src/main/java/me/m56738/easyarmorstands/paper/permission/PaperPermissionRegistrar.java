package me.m56738.easyarmorstands.paper.permission;

import me.m56738.easyarmorstands.permission.PermissionRegistrar;
import org.bukkit.Server;
import org.bukkit.permissions.Permission;

import java.util.HashMap;
import java.util.Map;

public class PaperPermissionRegistrar implements PermissionRegistrar {
    private final Server server;
    private final Map<String, Permission> registeredPermissions = new HashMap<>();

    public PaperPermissionRegistrar(Server server) {
        this.server = server;
    }

    @Override
    public void registerPermission(String name, String description, Map<String, Boolean> children) {
        Permission permission = new Permission(name, description, children);
        try {
            server.getPluginManager().addPermission(permission);
        } catch (IllegalArgumentException e) {
            return;
        }
        registeredPermissions.put(name, permission);
    }

    public void unregisterAll() {
        for (Permission permission : registeredPermissions.values()) {
            server.getPluginManager().removePermission(permission);
        }
        registeredPermissions.clear();
    }
}
