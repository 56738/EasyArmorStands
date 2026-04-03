package me.m56738.easyarmorstands.fancyholograms.property.block;

import de.oliver.fancyholograms.api.data.BlockHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public class BlockHologramBlockProperty implements Property<BlockData> {
    private final Hologram hologram;
    private final BlockHologramData data;

    public BlockHologramBlockProperty(Hologram hologram, BlockHologramData data) {
        this.hologram = hologram;
        this.data = data;
    }

    @Override
    public @NotNull PropertyType<BlockData> getType() {
        return BlockDisplayPropertyTypes.BLOCK;
    }

    @Override
    public @NotNull BlockData getValue() {
        return data.getBlock().createBlockData();
    }

    @Override
    public boolean setValue(@NotNull BlockData value) {
        data.setBlock(value.getMaterial());
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
        return true;
    }
}
