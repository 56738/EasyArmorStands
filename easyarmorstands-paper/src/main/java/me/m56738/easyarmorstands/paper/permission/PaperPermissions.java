package me.m56738.easyarmorstands.paper.permission;

import me.m56738.easyarmorstands.common.permission.Permission;
import org.bukkit.Bukkit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class PaperPermissions {
    private static final Map<String, org.bukkit.permissions.Permission> registeredPermissions = new HashMap<>();

    private PaperPermissions() {
    }

    public static void registerAll(Collection<Permission> permissions) {
        for (Permission permission : permissions) {
            register(permission);
        }
    }

    public static void unregisterAll() {
        for (org.bukkit.permissions.Permission permission : registeredPermissions.values()) {
            Bukkit.getPluginManager().removePermission(permission);
        }
        registeredPermissions.clear();
    }

    public static void register(Permission permission) {
        org.bukkit.permissions.Permission nativePermission = new org.bukkit.permissions.Permission(
                permission.name(), permission.description(), permission.children());
        try {
            Bukkit.getPluginManager().addPermission(nativePermission);
        } catch (IllegalArgumentException e) {
            return;
        }
        registeredPermissions.put(permission.name(), nativePermission);
    }

    public static void unregister(Permission permission) {
        Bukkit.getPluginManager().removePermission(registeredPermissions.remove(permission.name()));
    }
}
