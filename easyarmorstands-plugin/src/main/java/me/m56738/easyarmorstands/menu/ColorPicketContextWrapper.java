package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
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
    public @NotNull ItemStack item() {
        return context.item();
    }

    @Override
    public @NotNull Color getColor() {
        return context.getColor();
    }

    @Override
    public void setColor(@NotNull Color color) {
        context.setColor(color);
        for (Runnable subscription : subscriptions) {
            subscription.run();
        }
    }

    public void subscribe(@NotNull Runnable callback) {
        subscriptions.add(callback);
    }
}
