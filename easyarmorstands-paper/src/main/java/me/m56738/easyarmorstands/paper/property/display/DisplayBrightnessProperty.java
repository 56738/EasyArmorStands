package me.m56738.easyarmorstands.paper.property.display;

import me.m56738.easyarmorstands.api.platform.entity.display.Brightness;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.Display;

import java.util.Optional;

public class DisplayBrightnessProperty extends EntityProperty<Display, Optional<Brightness>> {
    public DisplayBrightnessProperty(Display entity) {
        super(entity);
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
}
