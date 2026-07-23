package me.m56738.easyarmorstands.menu.color;

import me.m56738.easyarmorstands.platform.color.RGBColor;
import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ColorPicketContextWrapper implements ColorPickerContext {
    private final ColorPickerContext context;
    private final List<Runnable> subscriptions = new ArrayList<>();

    public ColorPicketContextWrapper(ColorPickerContext context) {
        this.context = context;
    }

    @Override
    public @NotNull Platform platform() {
        return context.platform();
    }

    @Override
    public @NotNull ItemStack item() {
        return context.item();
    }

    @Override
    public @NotNull RGBColor getColor() {
        return context.getColor();
    }

    @Override
    public void setColor(@NotNull RGBColor color) {
        context.setColor(color);
        for (Runnable subscription : subscriptions) {
            subscription.run();
        }
    }

    public void subscribe(@NotNull Runnable callback) {
        subscriptions.add(callback);
    }
}
