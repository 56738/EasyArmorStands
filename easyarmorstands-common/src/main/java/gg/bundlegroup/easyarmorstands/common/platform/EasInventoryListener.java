package gg.bundlegroup.easyarmorstands.common.platform;

public interface EasInventoryListener {
    boolean onClick(int slot, boolean click, boolean put, boolean take, EasItem cursor);

    void update();
}
