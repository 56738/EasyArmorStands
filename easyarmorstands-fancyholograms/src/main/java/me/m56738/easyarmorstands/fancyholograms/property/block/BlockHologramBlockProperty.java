package me.m56738.easyarmorstands.fancyholograms.property.block;

import de.oliver.fancyholograms.api.data.BlockHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.platform.world.BlockData;
import me.m56738.easyarmorstands.api.property.type.BlockDisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.fancyholograms.property.HologramProperty;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperBlockData;
import org.jetbrains.annotations.NotNull;

public class BlockHologramBlockProperty extends HologramProperty<BlockData> {
    private final BlockHologramData data;

    public BlockHologramBlockProperty(Hologram hologram, BlockHologramData data) {
        super(hologram);
        this.data = data;
    }

    @Override
    public @NotNull PropertyType<BlockData> getType() {
        return BlockDisplayPropertyTypes.BLOCK;
    }

    @Override
    public @NotNull BlockData getValue() {
        return PaperBlockData.fromNative(data.getBlock().createBlockData());
    }

    @Override
    public boolean setValue(@NotNull BlockData value) {
        data.setBlock(PaperBlockData.toNative(value).getMaterial());
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
        return true;
    }
}
