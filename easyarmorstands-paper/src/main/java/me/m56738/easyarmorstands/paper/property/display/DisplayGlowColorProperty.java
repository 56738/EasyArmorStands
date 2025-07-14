package me.m56738.easyarmorstands.paper.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.Color;
import org.bukkit.entity.Display;

import java.util.Optional;

public class DisplayGlowColorProperty implements Property<Optional<Color>> {
    private final Display entity;

    public DisplayGlowColorProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Optional<Color>> getType() {
        return DisplayPropertyTypes.GLOW_COLOR;
    }

    @Override
    public Optional<Color> getValue() {
        return Optional.ofNullable(entity.getGlowColorOverride());
    }

    @Override
    public boolean setValue(Optional<Color> value) {
        entity.setGlowColorOverride(value.orElse(null));
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
