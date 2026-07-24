package me.m56738.easyarmorstands.platform.block;

import me.m56738.easyarmorstands.platform.inventory.ItemType;

public interface BlockData {
    String getAsString();

    ItemType getPlacementItemType();
}
