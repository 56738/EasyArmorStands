package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ColorAxisSlotFactory implements MenuSlotFactory {
    private final ColorAxis axis;
    private final SimpleItemTemplate itemTemplate;

    public ColorAxisSlotFactory(ColorAxis axis, SimpleItemTemplate itemTemplate) {
        this.axis = axis;
        this.itemTemplate = itemTemplate;
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context) {
        ColorPickerContext colorPickerContext = context.colorPicker();
        if (colorPickerContext != null) {
            return new ColorAxisSlot(
                    colorPickerContext,
                    axis,
                    itemTemplate,
                    TagResolver.empty());
        } else {
            return null;
        }
    }
}
