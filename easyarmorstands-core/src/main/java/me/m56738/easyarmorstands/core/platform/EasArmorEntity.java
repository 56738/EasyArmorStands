package me.m56738.easyarmorstands.core.platform;

public interface EasArmorEntity extends EasEntity {
    EasItem getItem(Slot slot);

    void setItem(Slot slot, EasItem item);

    enum Slot {
        HEAD,
        BODY,
        OFF_HAND,
        MAIN_HAND,
        LEGS,
        FEET
    }
}
