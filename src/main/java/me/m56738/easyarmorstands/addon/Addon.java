package me.m56738.easyarmorstands.addon;

import me.m56738.easyarmorstands.EasyArmorStands;

public interface Addon {
    boolean isSupported();

    String getName();

    void enable(EasyArmorStands plugin);
}
