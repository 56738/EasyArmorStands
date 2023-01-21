package gg.bundlegroup.easyarmorstands.bukkit.addon;

import gg.bundlegroup.easyarmorstands.bukkit.EasyArmorStands;

public interface Addon {
    boolean isSupported();

    String getName();

    void enable(EasyArmorStands plugin);
}
