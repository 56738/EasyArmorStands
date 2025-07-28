package me.m56738.easyarmorstands.modded.api.platform.world;

import me.m56738.easyarmorstands.api.platform.world.World;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public interface ModdedWorld extends World {
    static ModdedWorld fromNative(Level nativeWorld) {
        return new ModdedWorldImpl((ServerLevel) nativeWorld);
    }

    static ServerLevel toNative(World world) {
        return ((ModdedWorld) world).getNative();
    }

    ServerLevel getNative();
}
