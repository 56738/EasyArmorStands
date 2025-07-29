package me.m56738.easyarmorstands.paper.property.display.block;

import me.m56738.easyarmorstands.api.platform.world.BlockData;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperBlockData;
import org.bukkit.entity.BlockDisplay;

public class BlockDisplayBlockProperty implements Property<BlockData> {
    private final BlockDisplay entity;

    public BlockDisplayBlockProperty(BlockDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<BlockData> getType() {
        return BlockDisplayPropertyTypes.BLOCK;
    }

    @Override
    public BlockData getValue() {
        return PaperBlockData.fromNative(entity.getBlock());
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
