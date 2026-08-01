package me.m56738.easyarmorstands.fabric.api;

import me.m56738.easyarmorstands.modded.api.EasyArmorStandsModded;

public interface EasyArmorStandsFabric extends EasyArmorStandsModded {
    static EasyArmorStandsFabric get() {
        EasyArmorStandsFabric instance = EasyArmorStandsFabricHolder.getInstance();
        if (instance == null) {
            throw new IllegalArgumentException();
        }
        return instance;
    }
}
