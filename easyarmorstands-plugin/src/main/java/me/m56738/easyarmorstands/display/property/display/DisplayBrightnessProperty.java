package me.m56738.easyarmorstands.display.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import org.bukkit.entity.Display;
import org.bukkit.entity.Display.Brightness;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DisplayBrightnessProperty implements Property<Optional<Brightness>> {
    private final Display entity;

    public DisplayBrightnessProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Optional<Brightness>> getType() {
        return DisplayPropertyTypes.BRIGHTNESS;
    }

    @Override
    public @NotNull Optional<Brightness> getValue() {
        return Optional.ofNullable(entity.getBrightness());
    }

    @Override
    public boolean setValue(@NotNull Optional<Brightness> value) {
        entity.setBrightness(value.orElse(null));
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
