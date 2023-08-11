package me.m56738.easyarmorstands.property.v1_19_4.display.block;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockDisplayBlockProperty implements Property<BlockData> {
    public static final PropertyType<BlockData> TYPE = new Type();
    private final BlockDisplay entity;

    public BlockDisplayBlockProperty(BlockDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<BlockData> getType() {
        return TYPE;
    }

    @Override
    public BlockData getValue() {
        return entity.getBlock();
    }

    @Override
    public boolean setValue(BlockData value) {
        entity.setBlock(value);
        return true;
    }

    private static class Type implements PropertyType<BlockData> {
        @Override
        public @Nullable String getPermission() {
            return "easyarmorstands.property.display.block";
        }

        @Override
        public @NotNull Component getDisplayName() {
            return Component.text("block");
        }

        @Override
        public @NotNull Component getValueComponent(BlockData value) {
            return Component.text(value.getAsString());
        }
    }
}
