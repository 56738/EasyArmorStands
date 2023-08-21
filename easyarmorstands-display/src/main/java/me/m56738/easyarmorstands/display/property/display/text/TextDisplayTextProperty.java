package me.m56738.easyarmorstands.display.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.display.capability.textdisplay.TextDisplayCapability;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

public class TextDisplayTextProperty implements Property<Component> {
    private final TextDisplay entity;
    private final TextDisplayCapability textDisplayCapability;

    public TextDisplayTextProperty(TextDisplay entity, TextDisplayCapability textDisplayCapability) {
        this.entity = entity;
        this.textDisplayCapability = textDisplayCapability;
    }

    @Override
    public @NotNull PropertyType<Component> getType() {
        return DisplayPropertyTypes.TEXT_DISPLAY_TEXT;
    }

    @Override
    public Component getValue() {
        return textDisplayCapability.getText(entity);
    }

    @Override
    public boolean setValue(Component value) {
        textDisplayCapability.setText(entity, value);
        return true;
    }
}
