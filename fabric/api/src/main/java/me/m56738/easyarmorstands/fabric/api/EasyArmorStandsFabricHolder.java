package me.m56738.easyarmorstands.fabric.api;

import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@ApiStatus.Internal
public final class EasyArmorStandsFabricHolder {
    private static final Map<MinecraftServer, EasyArmorStandsFabric> instances = new HashMap<>();

    private EasyArmorStandsFabricHolder() {
    }

    public static void setInstance(MinecraftServer server, EasyArmorStandsFabric instance) {
        synchronized (instances) {
            instances.put(server, instance);
        }
    }

    public static @Nullable EasyArmorStandsFabric removeInstance(MinecraftServer server) {
        synchronized (instances) {
            return instances.remove(server);
        }
    }

    public static @Nullable EasyArmorStandsFabric getInstance(MinecraftServer server) {
        synchronized (instances) {
            return instances.get(server);
        }
    }
}
