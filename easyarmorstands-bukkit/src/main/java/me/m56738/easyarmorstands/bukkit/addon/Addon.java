package me.m56738.easyarmorstands.bukkit.addon;

import me.m56738.easyarmorstands.bukkit.EasyArmorStands;

public interface Addon {
    boolean isSupported();

    String getName();

    void enable(EasyArmorStands plugin);
}
