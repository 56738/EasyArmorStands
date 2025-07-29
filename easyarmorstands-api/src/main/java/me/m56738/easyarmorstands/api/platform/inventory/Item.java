package me.m56738.easyarmorstands.api.platform.inventory;

import net.kyori.adventure.text.Component;

public interface Item {
    Component displayName();

    boolean isEmpty();

    boolean isSkull();
}
