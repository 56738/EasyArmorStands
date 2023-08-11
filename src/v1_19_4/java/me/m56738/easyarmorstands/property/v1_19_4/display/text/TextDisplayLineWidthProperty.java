package me.m56738.easyarmorstands.property.v1_19_4.display.text;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.TextDisplay;

public class TextDisplayLineWidthProperty implements Property<Integer> {
    public static final PropertyType<Integer> TYPE = new Type();
    private final TextDisplay entity;

    public TextDisplayLineWidthProperty(TextDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Integer> getType() {
        return TYPE;
    }

    @Override
    public Integer getValue() {
        return entity.getLineWidth();
    }

    @Override
    public boolean setValue(Integer value) {
        entity.setLineWidth(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    private static class Type implements PropertyType<Integer> {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.display.text.linewidth";
        }

        @Override
        public Component getDisplayName() {
            return Component.text("line width");
        }

        @Override
        public Component getValueComponent(Integer value) {
            return Component.text(value);
        }
    }
}
