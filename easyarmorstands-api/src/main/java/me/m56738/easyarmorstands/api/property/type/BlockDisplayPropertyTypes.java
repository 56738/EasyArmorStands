package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.platform.world.BlockData;
import org.jspecify.annotations.NullMarked;

import static net.kyori.adventure.key.Key.key;
import static net.kyori.adventure.text.Component.translatable;

@NullMarked
public final class BlockDisplayPropertyTypes {
    public static final PropertyType<BlockData> BLOCK = PropertyType.build(b -> b
            .key(key(EasyArmorStands.NAMESPACE, "block_display/block"))
            .name(translatable("easyarmorstands.property.block-display.block.name"))
            .permission("easyarmorstands.property.display.block"));

    private BlockDisplayPropertyTypes() {
    }
}
