package gg.bundlegroup.easyarmorstands.platform;

public interface EasInventory extends EasWrapper {
    EasItem getItem(int slot);

    void setItem(int slot, EasItem item);
}
