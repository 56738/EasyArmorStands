package me.m56738.easyarmorstands.paper.property.display.block;

import me.m56738.easyarmorstands.api.platform.world.BlockData;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperBlockData;
import org.bukkit.entity.BlockDisplay;

public class BlockDisplayBlockProperty implements Property<BlockData> {
    private final PaperPlatform platform;
    private final BlockDisplay entity;

    public BlockDisplayBlockProperty(PaperPlatform platform, BlockDisplay entity) {
        this.platform = platform;
        this.entity = entity;
    }

    @Override
    public PropertyType<BlockData> getType() {
        return BlockDisplayPropertyTypes.BLOCK;
    }

    @Override
    public BlockData getValue() {
        return platform.getBlockData(entity.getBlock());
    }

    @Override
    public boolean setValue(BlockData value) {
        entity.setBlock(PaperBlockData.toNative(value));
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
