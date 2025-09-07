package me.m56738.easyarmorstands.modded.api.platform.world;

import me.m56738.easyarmorstands.api.platform.world.Block;
import me.m56738.easyarmorstands.api.platform.world.World;
import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatformHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.joml.Vector3ic;

public interface ModdedWorld extends World, ModdedPlatformHolder {
    static ServerLevel toNative(World world) {
        return ((ModdedWorld) world).getNative();
    }

    ServerLevel getNative();

    @Override
    default Block getBlock(Vector3ic position) {
        return getBlock(new BlockPos(position.x(), position.y(), position.z()));
    }

    default Block getBlock(BlockPos position) {
        return getPlatform().getBlock(getNative(), position);
    }
}
