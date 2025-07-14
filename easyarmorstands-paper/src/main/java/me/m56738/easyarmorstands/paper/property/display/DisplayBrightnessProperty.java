package me.m56738.easyarmorstands.paper.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Display;
import org.bukkit.entity.Display.Brightness;

import java.util.Optional;

public class DisplayBrightnessProperty implements Property<Optional<Brightness>> {
    private final Display entity;

    public DisplayBrightnessProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Optional<Brightness>> getType() {
        return DisplayPropertyTypes.BRIGHTNESS;
    }

    @Override
    public Optional<Brightness> getValue() {
        return Optional.ofNullable(entity.getBrightness());
    }

    @Override
    public boolean setValue(Optional<Brightness> value) {
        entity.setBrightness(value.orElse(null));
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
