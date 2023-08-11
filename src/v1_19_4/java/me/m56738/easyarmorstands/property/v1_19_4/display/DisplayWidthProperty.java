package me.m56738.easyarmorstands.property.v1_19_4.display;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;

public class DisplayWidthProperty implements Property<Float> {
    public static final PropertyType<Float> TYPE = new DisplaySizePropertyType(Component.text("width"));
    private final Display entity;

    public DisplayWidthProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Float> getType() {
        return TYPE;
    }

    @Override
    public Float getValue() {
        return entity.getDisplayWidth();
    }

    @Override
    public boolean setValue(Float value) {
        entity.setDisplayWidth(value);
        return true;
    }

}
