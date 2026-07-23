package me.m56738.easyarmorstands.platform.block;

import me.m56738.easyarmorstands.platform.inventory.ItemType;
import org.jspecify.annotations.Nullable;

public interface BlockData {
    String getAsString();

    @Nullable ItemType getPlacementItemType();
}
