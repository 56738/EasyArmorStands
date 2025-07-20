package me.m56738.easyarmorstands.api.platform;

import me.m56738.easyarmorstands.api.platform.item.Item;

public interface Platform {
    String getEasyArmorStandsVersion();

    Item createTool();

    boolean isTool(Item item);
}
