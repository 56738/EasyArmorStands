package me.m56738.easyarmorstands.paper.api.platform.world;

import me.m56738.easyarmorstands.api.platform.world.Block;
import me.m56738.easyarmorstands.api.platform.world.World;
import org.joml.Vector3ic;

public interface PaperWorld extends World {
    static PaperWorld fromNative(org.bukkit.World nativeWorld) {
        return new PaperWorldImpl(nativeWorld);
    }

    static org.bukkit.World toNative(World world) {
        return ((PaperWorld) world).getNative();
    }

    org.bukkit.World getNative();

    @Override
    default Block getBlock(Vector3ic position) {
        return PaperBlock.fromNative(getNative().getBlockAt(position.x(), position.y(), position.z()));
    }
}
