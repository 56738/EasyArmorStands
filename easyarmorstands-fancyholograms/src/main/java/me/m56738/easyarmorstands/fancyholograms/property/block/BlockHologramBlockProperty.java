package me.m56738.easyarmorstands.fancyholograms.property.block;

import de.oliver.fancyholograms.api.data.BlockHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.platform.world.BlockData;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.fancyholograms.property.HologramProperty;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperBlockData;

public class BlockHologramBlockProperty extends HologramProperty<BlockData> {
    private final PaperPlatform platform;
    private final BlockHologramData data;

    public BlockHologramBlockProperty(PaperPlatform platform, Hologram hologram, BlockHologramData data) {
        super(hologram);
        this.platform = platform;
        this.data = data;
    }

    @Override
    public PropertyType<BlockData> getType() {
        return BlockDisplayPropertyTypes.BLOCK;
    }

    @Override
    public BlockData getValue() {
        return platform.getBlockData(data.getBlock().createBlockData());
    }

    @Override
    public boolean setValue(BlockData value) {
        data.setBlock(PaperBlockData.toNative(value).getMaterial());
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
        return true;
    }
}
