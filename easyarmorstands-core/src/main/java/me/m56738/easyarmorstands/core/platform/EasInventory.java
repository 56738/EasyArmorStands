package me.m56738.easyarmorstands.core.platform;

public interface EasInventory extends EasWrapper {
    EasItem getItem(int slot);

    void setItem(int slot, EasItem item);
}
