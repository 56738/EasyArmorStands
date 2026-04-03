package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ColorAxisChangeSlotFactory implements MenuSlotFactory {
    private final ColorAxis axis;
    private final SimpleItemTemplate itemTemplate;
    private final int leftChange;
    private final int rightChange;
    private final int shiftChange;

    public ColorAxisChangeSlotFactory(ColorAxis axis, SimpleItemTemplate itemTemplate, int leftChange, int rightChange, int shiftChange) {
        this.axis = axis;
        this.itemTemplate = itemTemplate;
        this.leftChange = leftChange;
        this.rightChange = rightChange;
        this.shiftChange = shiftChange;
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context) {
        ColorPickerContext colorPickerContext = context.colorPicker();
        if (colorPickerContext != null) {
            return new ColorAxisChangeSlot(
                    colorPickerContext,
                    axis,
                    itemTemplate,
                    TagResolver.empty(),
                    leftChange,
                    rightChange,
                    shiftChange);
        } else {
            return null;
        }
    }
}
