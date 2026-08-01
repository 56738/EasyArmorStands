package me.m56738.easyarmorstands.fabric.api;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

@ApiStatus.Internal
public final class EasyArmorStandsFabricHolder {
    private static @Nullable EasyArmorStandsFabric instance;

    public static @Nullable EasyArmorStandsFabric getInstance() {
        return instance;
    }

    public static void setInstance(@Nullable EasyArmorStandsFabric instance) {
        EasyArmorStandsFabricHolder.instance = instance;
    }
}
