package me.m56738.easyarmorstands.platform.paper.block;

import me.m56738.easyarmorstands.platform.block.BlockData;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemType;
import org.jspecify.annotations.Nullable;

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
    default @Nullable PaperItemType getPlacementItemType() {
        Material material = getNative().getPlacementMaterial();
        if (material.isAir()) {
            return PaperItemType.fromNative(ItemType.AIR);
        }
        ItemType itemType = material.asItemType();
        if (itemType == null) {
            return null;
        }
        return PaperItemType.fromNative(itemType);
    }
}
