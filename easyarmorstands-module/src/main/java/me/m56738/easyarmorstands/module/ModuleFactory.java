package me.m56738.easyarmorstands.module;

import org.bukkit.plugin.Plugin;

public interface ModuleFactory {
    String name();

    boolean supported();

    Module create(Plugin plugin);
}
