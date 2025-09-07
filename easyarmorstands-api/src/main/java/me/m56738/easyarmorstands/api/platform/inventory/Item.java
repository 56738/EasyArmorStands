package me.m56738.easyarmorstands.api.platform.inventory;

import me.m56738.easyarmorstands.api.platform.PlatformHolder;
import net.kyori.adventure.text.Component;

public interface Item extends PlatformHolder {
    Component getDisplayName();

    boolean isEmpty();

    boolean isSkull();
}
