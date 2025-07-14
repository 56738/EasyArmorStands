package me.m56738.easyarmorstands.paper.property.display.block;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.block.data.BlockData;
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
        return entity.getBlock();
    }

    @Override
    public boolean setValue(BlockData value) {
        entity.setBlock(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
