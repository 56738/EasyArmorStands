package me.m56738.easyarmorstands.paper.api.platform.world;

import me.m56738.easyarmorstands.api.platform.world.Block;
import me.m56738.easyarmorstands.api.platform.world.World;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatformHolder;
import org.joml.Vector3ic;

public interface PaperWorld extends World, PaperPlatformHolder {
    static org.bukkit.World toNative(World world) {
        return ((PaperWorld) world).getNative();
    }

    org.bukkit.World getNative();

    @Override
    default Block getBlock(Vector3ic position) {
        return getPlatform().getBlock(getNative().getBlockAt(position.x(), position.y(), position.z()));
    }
}
