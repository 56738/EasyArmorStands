package me.m56738.easyarmorstands.permission;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class PermissionLoader {
    private final Plugin plugin;
    private final Set<String> blacklist = new HashSet<>();

    public PermissionLoader(Plugin plugin) {
        this.plugin = plugin;
        this.blacklist.add("description");
        this.blacklist.add("children");
    }

    public void load(ConfigurationSection section) {
        if (section.isString("description")) {
            String name = section.getCurrentPath();
            String description = section.getString("description");
            List<String> childrenList = section.getStringList("children");
            Map<String, Boolean> children = new HashMap<>(childrenList.size());
            for (String child : childrenList) {
                children.put(child, true);
            }
            Permission permission = new Permission(name, description, children);
            try {
                Bukkit.getPluginManager().addPermission(permission);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().log(Level.WARNING, "Failed to register permission " + name, e);
            }
        }

        for (String key : section.getKeys(false)) {
            if (!blacklist.contains(key)) {
                ConfigurationSection child = section.getConfigurationSection(key);
                if (child != null) {
                    load(child);
                }
            }
        }
    }
}
