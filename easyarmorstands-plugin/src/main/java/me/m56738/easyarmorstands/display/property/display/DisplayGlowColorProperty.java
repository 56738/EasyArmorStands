package me.m56738.easyarmorstands.display.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import org.bukkit.Color;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DisplayGlowColorProperty implements Property<Optional<Color>> {
    private final Display entity;

    public DisplayGlowColorProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Optional<Color>> getType() {
        return DisplayPropertyTypes.GLOW_COLOR;
    }

    @Override
    public @NotNull Optional<Color> getValue() {
        return Optional.ofNullable(entity.getGlowColorOverride());
    }

    @Override
    public boolean setValue(@NotNull Optional<Color> value) {
        entity.setGlowColorOverride(value.orElse(null));
        return true;
    }
}
