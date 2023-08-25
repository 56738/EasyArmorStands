package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.property.PropertyMap;
import me.m56738.easyarmorstands.display.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.display.editor.node.DisplayRootNodeFactory;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;

public class TextDisplayElementType extends DisplayElementType<TextDisplay> {
    public TextDisplayElementType(DisplayRootNodeFactory<TextDisplay> factory) {
        super(EntityType.TEXT_DISPLAY, TextDisplay.class, factory);
    }

    @Override
    public void applyDefaultProperties(PropertyMap properties) {
        super.applyDefaultProperties(properties);
        properties.put(TextDisplayPropertyTypes.TEXT, Component.text("Text"));
    }
}
