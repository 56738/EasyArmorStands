package me.m56738.easyarmorstands.display.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.TextDisplayPropertyTypes;
import org.bukkit.Color;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation") // presence checked in isSupported
public class TextDisplayBackgroundProperty implements Property<@Nullable Color> {
    private final TextDisplay entity;

    public TextDisplayBackgroundProperty(TextDisplay entity) {
        this.entity = entity;
    }

    public static boolean isSupported() {
        try {
            TextDisplay.class.getMethod("getBackgroundColor");
            TextDisplay.class.getMethod("setBackgroundColor", Color.class);
            TextDisplay.class.getMethod("isDefaultBackground");
            TextDisplay.class.getMethod("setDefaultBackground", boolean.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public @NotNull PropertyType<@Nullable Color> getType() {
        return TextDisplayPropertyTypes.BACKGROUND;
    }

    @Override
    public @Nullable Color getValue() {
        if (entity.isDefaultBackground()) {
            return null;
        }
        Color color = entity.getBackgroundColor();
        if (color == null) {
            color = Color.WHITE;
        }
        return color;
    }

    @Override
    public boolean setValue(@Nullable Color value) {
        if (value != null) {
            entity.setDefaultBackground(false);
            entity.setBackgroundColor(value);
        } else {
            entity.setDefaultBackground(true);
            entity.setBackgroundColor(null);
        }
        return true;
    }
}
