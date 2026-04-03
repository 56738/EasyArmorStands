package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.menu.slot.BlockDisplaySlot;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockDataPropertyType extends ConfigurablePropertyType<BlockData> {
    public BlockDataPropertyType(@NotNull Key key) {
        super(key);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull BlockData value) {
        return Component.text(value.getAsString());
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull Property<BlockData> property, @NotNull PropertyContainer container) {
        return new BlockDisplaySlot(property, container, buttonTemplate);
    }
}
