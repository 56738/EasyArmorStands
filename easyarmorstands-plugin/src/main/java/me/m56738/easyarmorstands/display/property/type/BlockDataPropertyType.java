package me.m56738.easyarmorstands.display.property.type;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.layout.ContentMenuLayout;
import me.m56738.easyarmorstands.api.menu.layout.MenuLayout;
import me.m56738.easyarmorstands.display.menu.BlockDisplaySlot;
import me.m56738.easyarmorstands.property.type.ConfigurablePropertyType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockDataPropertyType extends ConfigurablePropertyType<BlockData> {
    public BlockDataPropertyType(@NotNull Key key) {
        super(key, BlockData.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull BlockData value) {
        return Component.text(value.getAsString());
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull Element element) {
        return new BlockDisplaySlot(element, this, buttonTemplate);
    }

    @Override
    public void addToMenu(@NotNull MenuLayout layout, @NotNull Element element) {
        MenuSlot slot = createSlot(element);
        if (slot != null) {
            if (layout instanceof ContentMenuLayout contentMenuLayout) {
                contentMenuLayout.setContentSlot(slot);
            } else {
                layout.addSlot(slot);
            }
        }
    }
}
