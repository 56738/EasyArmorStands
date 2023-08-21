package me.m56738.easyarmorstands.display.property.type;

import me.m56738.easyarmorstands.property.type.ConfigurablePropertyType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public class BlockDataPropertyType extends ConfigurablePropertyType<BlockData> {
    public BlockDataPropertyType(@NotNull Key key) {
        super(key, BlockData.class);
    }

    @Override
    public Component getValueComponent(BlockData value) {
        return Component.text(value.getAsString());
    }
}
