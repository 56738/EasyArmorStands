package me.m56738.easyarmorstands.property.v1_19_4.display.text;

import me.m56738.easyarmorstands.capability.textdisplay.TextDisplayCapability;
import me.m56738.easyarmorstands.property.ComponentPropertyType;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.TextDisplay;

public class TextDisplayTextProperty implements Property<Component> {
    public static final PropertyType<Component> TYPE = new Type();
    private final TextDisplay entity;
    private final TextDisplayCapability textDisplayCapability;

    public TextDisplayTextProperty(TextDisplay entity, TextDisplayCapability textDisplayCapability) {
        this.entity = entity;
        this.textDisplayCapability = textDisplayCapability;
    }

    @Override
    public PropertyType<Component> getType() {
        return TYPE;
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

    private static class Type implements ComponentPropertyType {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.display.text";
        }

        @Override
        public Component getDisplayName() {
            return Component.text("text");
        }
    }
}
