package me.m56738.easyarmorstands.property.v1_19_4.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayPropertyTypes;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.TextDisplay.TextAlignment;
import org.jetbrains.annotations.NotNull;

public class TextDisplayAlignmentProperty implements Property<TextAlignment> {
    private final TextDisplay entity;

    public TextDisplayAlignmentProperty(TextDisplay entity) {
        this.entity = entity;
    }

    public static boolean isSupported() {
        try {
            TextDisplay.class.getMethod("getAlignment");
            TextDisplay.class.getMethod("setAlignment", TextAlignment.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public @NotNull PropertyType<TextAlignment> getType() {
        return DisplayPropertyTypes.TEXT_DISPLAY_ALIGNMENT;
    }

    @Override
    public TextAlignment getValue() {
        return entity.getAlignment();
    }

    @Override
    public boolean setValue(TextAlignment value) {
        entity.setAlignment(value);
        return true;
    }
}
