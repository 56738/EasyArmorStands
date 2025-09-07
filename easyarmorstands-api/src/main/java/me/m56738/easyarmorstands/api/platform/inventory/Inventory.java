package me.m56738.easyarmorstands.api.platform.inventory;

import me.m56738.easyarmorstands.api.platform.PlatformHolder;

public interface Inventory extends PlatformHolder {
    int getSize();

    Item getItem(int slot);

    void setItem(int slot, Item item);
}
