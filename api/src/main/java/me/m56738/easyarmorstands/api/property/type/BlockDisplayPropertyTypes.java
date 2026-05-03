package me.m56738.easyarmorstands.api.property.type;

import org.bukkit.block.data.BlockData;
import org.jspecify.annotations.NullMarked;

import static me.m56738.easyarmorstands.api.EasyArmorStands.key;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

@NullMarked
public final class BlockDisplayPropertyTypes {
    public static final PropertyType<BlockData> BLOCK = PropertyType.builder(key("block_display/block"), BlockData.class)
            .name(translatable("easyarmorstands.property.block-display.block.name"))
            .formatter(value -> text(value.getAsString()))
            .permission("easyarmorstands.property.display.block")
            .build();

    private BlockDisplayPropertyTypes() {
    }
}
