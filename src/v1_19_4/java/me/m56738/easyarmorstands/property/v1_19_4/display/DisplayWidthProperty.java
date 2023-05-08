package me.m56738.easyarmorstands.property.v1_19_4.display;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;

public class DisplayWidthProperty extends DisplaySizeProperty {
    @Override
    public Float getValue(Display entity) {
        return entity.getDisplayWidth();
    }

    @Override
    public void setValue(Display entity, Float value) {
        entity.setDisplayWidth(value);
    }

    @Override
    public @NotNull String getName() {
        return "width";
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("width");
    }
}
