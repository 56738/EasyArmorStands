package me.m56738.easyarmorstands.platform.modded.block;

import me.m56738.easyarmorstands.platform.block.BlockData;
import me.m56738.easyarmorstands.platform.inventory.ItemType;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import me.m56738.easyarmorstands.platform.modded.inventory.ModdedItemType;
import net.minecraft.world.level.block.state.BlockState;

public interface ModdedBlockData extends BlockData, ModdedPlatformHolder {
    BlockState getNative();

    static ModdedBlockData fromNative(ModdedPlatform platform, BlockState state) {
        return new ModdedBlockDataImpl(platform, state);
    }

    static BlockState toNative(BlockData data) {
        return ((ModdedBlockData) data).getNative();
    }

    @Override
    default String getAsString() {
        return getNative().toString();
    }

    @Override
    default ItemType getPlacementItemType() {
        return ModdedItemType.fromNative(getPlatform(), getNative().getBlock().asItem());
    }
}
