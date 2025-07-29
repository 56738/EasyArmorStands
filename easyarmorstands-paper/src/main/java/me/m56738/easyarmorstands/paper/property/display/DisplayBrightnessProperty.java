package me.m56738.easyarmorstands.paper.property.display;

import me.m56738.easyarmorstands.api.platform.entity.display.Brightness;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Display;

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
        return Optional.ofNullable(entity.getBrightness())
                .map(brightness -> Brightness.of(brightness.getBlockLight(), brightness.getSkyLight()));
    }

    @Override
    public boolean setValue(Optional<Brightness> value) {
        entity.setBrightness(value
                .map(brightness -> new Display.Brightness(brightness.block(), brightness.sky()))
                .orElse(null));
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
