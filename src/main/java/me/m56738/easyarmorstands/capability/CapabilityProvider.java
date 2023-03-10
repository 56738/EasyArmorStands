package me.m56738.easyarmorstands.capability;

import org.bukkit.plugin.Plugin;

public interface CapabilityProvider<T> {
    boolean isSupported();

    Priority getPriority();

    T create(Plugin plugin);
}
