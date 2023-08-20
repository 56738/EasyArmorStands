package me.m56738.easyarmorstands.property.v1_19_4.display.block;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.type.PropertyType;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayPropertyTypes;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;

public class BlockDisplayBlockProperty implements Property<BlockData> {
    private final BlockDisplay entity;

    public BlockDisplayBlockProperty(BlockDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<BlockData> getType() {
        return DisplayPropertyTypes.BLOCK_DISPLAY_BLOCK;
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
}
