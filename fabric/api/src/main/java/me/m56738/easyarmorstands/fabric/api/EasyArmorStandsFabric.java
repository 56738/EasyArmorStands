package me.m56738.easyarmorstands.fabric.api;

import me.m56738.easyarmorstands.modded.api.EasyArmorStandsModded;
import net.minecraft.server.MinecraftServer;

public interface EasyArmorStandsFabric extends EasyArmorStandsModded {
    static EasyArmorStandsFabric get(MinecraftServer server) {
        EasyArmorStandsFabric instance = EasyArmorStandsFabricHolder.getInstance(server);
        if (instance == null) {
            throw new IllegalArgumentException();
        }
        return instance;
    }
}
