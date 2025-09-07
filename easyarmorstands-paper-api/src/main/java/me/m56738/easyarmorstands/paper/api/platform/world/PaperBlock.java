package me.m56738.easyarmorstands.paper.api.platform.world;

import me.m56738.easyarmorstands.api.platform.entity.display.Brightness;
import me.m56738.easyarmorstands.api.platform.world.Block;
import me.m56738.easyarmorstands.api.platform.world.BlockData;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatformHolder;

public interface PaperBlock extends Block, PaperPlatformHolder {
    static org.bukkit.block.Block toNative(Block block) {
        return ((PaperBlock) block).getNative();
    }

    org.bukkit.block.Block getNative();

    @Override
    default BlockData getBlockData() {
        return getPlatform().getBlockData(getNative().getBlockData());
    }

    @Override
    default Brightness getBrightness() {
        org.bukkit.block.Block nativeBlock = getNative();
        return Brightness.of(nativeBlock.getLightFromBlocks(), nativeBlock.getLightFromSky());
    }
}
