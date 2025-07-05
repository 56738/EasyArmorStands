package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ColorPresetSlotFactory implements MenuSlotFactory {
    private final Color color;
    private final SimpleItemTemplate itemTemplate;

    public ColorPresetSlotFactory(Color color, SimpleItemTemplate itemTemplate) {
        this.color = color;
        this.itemTemplate = itemTemplate;
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context) {
        ColorPickerContext colorPickerContext = context.colorPicker();
        if (colorPickerContext != null) {
            return new ColorPresetSlot(
                    colorPickerContext,
                    color,
                    itemTemplate,
                    TagResolver.empty());
        } else {
            return null;
        }
    }
}
