package me.m56738.easyarmorstands.neoforge.api;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public final class EasyArmorStandsNeoForgeHolder {
    private static @Nullable EasyArmorStandsNeoForge instance;

    public static @Nullable EasyArmorStandsNeoForge getInstance() {
        return instance;
    }

    public static void setInstance(@Nullable EasyArmorStandsNeoForge instance) {
        EasyArmorStandsNeoForgeHolder.instance = instance;
    }
}
