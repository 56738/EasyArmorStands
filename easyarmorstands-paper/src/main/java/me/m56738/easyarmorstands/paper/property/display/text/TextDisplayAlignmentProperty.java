package me.m56738.easyarmorstands.paper.property.display.text;

import me.m56738.easyarmorstands.api.platform.entity.display.TextAlignment;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.TextDisplay;

public class TextDisplayAlignmentProperty extends EntityProperty<TextDisplay, TextAlignment> {
    public TextDisplayAlignmentProperty(TextDisplay entity) {
        super(entity);
    }

    @Override
    public PropertyType<TextAlignment> getType() {
        return TextDisplayPropertyTypes.ALIGNMENT;
    }

    @Override
    public TextAlignment getValue() {
        return switch (entity.getAlignment()) {
            case CENTER -> TextAlignment.CENTER;
            case LEFT -> TextAlignment.LEFT;
            case RIGHT -> TextAlignment.RIGHT;
        };
    }

    @Override
    public boolean setValue(TextAlignment value) {
        entity.setAlignment(switch (value) {
            case CENTER -> TextDisplay.TextAlignment.CENTER;
            case LEFT -> TextDisplay.TextAlignment.LEFT;
            case RIGHT -> TextDisplay.TextAlignment.RIGHT;
        });
        return true;
    }
}
