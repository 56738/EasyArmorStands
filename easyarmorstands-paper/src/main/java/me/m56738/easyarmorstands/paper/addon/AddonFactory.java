package me.m56738.easyarmorstands.paper.addon;

import me.m56738.easyarmorstands.paper.EasyArmorStandsPlugin;

public interface AddonFactory<T extends Addon> {
    boolean isEnabled();

    boolean isAvailable();

    T create(EasyArmorStandsPlugin plugin);
}
