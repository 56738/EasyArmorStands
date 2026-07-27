package me.m56738.easyarmorstands.neoforge.api;

import me.m56738.easyarmorstands.modded.api.EasyArmorStandsModded;

public interface EasyArmorStandsNeoForge extends EasyArmorStandsModded {
    static EasyArmorStandsNeoForge get() {
        EasyArmorStandsNeoForge instance = EasyArmorStandsNeoForgeHolder.getInstance();
        if (instance == null) {
            throw new IllegalStateException();
        }
        return instance;
    }
}
