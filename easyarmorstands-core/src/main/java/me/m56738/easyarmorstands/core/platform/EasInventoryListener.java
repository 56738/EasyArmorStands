package me.m56738.easyarmorstands.core.platform;

public interface EasInventoryListener {
    boolean onClick(int slot, boolean click, boolean put, boolean take, EasItem cursor);

    void update();
}
