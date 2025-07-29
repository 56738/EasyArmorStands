package me.m56738.easyarmorstands.modded.api.platform.world;

import me.m56738.easyarmorstands.api.platform.world.Block;
import me.m56738.easyarmorstands.api.platform.world.World;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.joml.Vector3ic;

public interface ModdedWorld extends World {
    static ModdedWorld fromNative(Level nativeWorld) {
        return new ModdedWorldImpl((ServerLevel) nativeWorld);
    }

    static ServerLevel toNative(World world) {
        return ((ModdedWorld) world).getNative();
    }

    ServerLevel getNative();

    @Override
    default Block getBlock(Vector3ic position) {
        return new ModdedBlockImpl(getNative(), new BlockPos(position.x(), position.y(), position.z()));
    }
}
