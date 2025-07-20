package me.m56738.easyarmorstands.modded.api.platform.world;

import me.m56738.easyarmorstands.api.platform.world.World;
import net.minecraft.world.level.Level;

public interface ModdedWorld extends World {
    static ModdedWorld fromNative(Level nativeWorld) {
        return new ModdedWorldImpl(nativeWorld);
    }

    static Level toNative(World world) {
        return ((ModdedWorld) world).getNative();
    }

    Level getNative();
}
