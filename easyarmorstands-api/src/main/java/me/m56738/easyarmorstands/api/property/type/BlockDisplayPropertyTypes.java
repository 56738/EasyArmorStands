package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.platform.world.BlockData;

import static me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes.get;

public final class BlockDisplayPropertyTypes {
    public static final PropertyType<BlockData> BLOCK = get("block_display/block");

    private BlockDisplayPropertyTypes() {
    }
}
