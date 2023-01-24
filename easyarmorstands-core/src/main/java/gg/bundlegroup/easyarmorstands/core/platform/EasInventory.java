package gg.bundlegroup.easyarmorstands.core.platform;

public interface EasInventory extends EasWrapper {
    EasItem getItem(int slot);

    void setItem(int slot, EasItem item);
}
