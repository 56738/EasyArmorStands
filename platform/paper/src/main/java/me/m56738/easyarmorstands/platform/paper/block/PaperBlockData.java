package me.m56738.easyarmorstands.platform.paper.block;

import me.m56738.easyarmorstands.platform.block.BlockData;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemType;
import org.bukkit.inventory.ItemType;

import java.util.Objects;

public interface PaperBlockData extends BlockData {
    static PaperBlockData fromNative(org.bukkit.block.data.BlockData data) {
        return new PaperBlockDataImpl(data);
    }

    org.bukkit.block.data.BlockData getNative();

    static org.bukkit.block.data.BlockData toNative(BlockData data) {
        return ((PaperBlockData) data).getNative();
    }

    @Override
    default String getAsString() {
        return getNative().getAsString();
    }

    @Override
    default PaperItemType getPlacementItemType() {
        ItemType itemType = getNative().getPlacementMaterial().asItemType();
        return PaperItemType.fromNative(Objects.requireNonNullElse(itemType, ItemType.AIR));
    }
}
