package me.m56738.easyarmorstands.paper.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import org.bukkit.entity.TextDisplay;
import org.bukkit.entity.TextDisplay.TextAlignment;

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
    public PropertyType<TextAlignment> getType() {
        return TextDisplayPropertyTypes.ALIGNMENT;
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

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
