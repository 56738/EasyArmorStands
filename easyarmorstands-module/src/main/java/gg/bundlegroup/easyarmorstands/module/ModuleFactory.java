package gg.bundlegroup.easyarmorstands.module;

import org.bukkit.plugin.Plugin;

public interface ModuleFactory {
    String name();

    boolean supported();

    Module create(Plugin plugin);
}
