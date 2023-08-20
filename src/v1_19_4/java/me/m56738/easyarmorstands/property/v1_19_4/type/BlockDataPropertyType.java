package me.m56738.easyarmorstands.property.v1_19_4.type;

import me.m56738.easyarmorstands.property.type.ConfigurablePropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.block.data.BlockData;

public class BlockDataPropertyType extends ConfigurablePropertyType<BlockData> {
    public BlockDataPropertyType(String key) {
        super(key);
    }

    @Override
    public Component getValueComponent(BlockData value) {
        return Component.text(value.getAsString());
    }
}
