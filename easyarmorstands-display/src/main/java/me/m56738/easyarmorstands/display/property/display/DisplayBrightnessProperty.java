package me.m56738.easyarmorstands.display.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import org.bukkit.entity.Display;
import org.bukkit.entity.Display.Brightness;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DisplayBrightnessProperty implements Property<@Nullable Brightness> {
    private final Display entity;

    public DisplayBrightnessProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<@Nullable Brightness> getType() {
        return DisplayPropertyTypes.DISPLAY_BRIGHTNESS;
    }

    @Override
    public @Nullable Brightness getValue() {
        return entity.getBrightness();
    }

    @Override
    public boolean setValue(@Nullable Brightness value) {
        entity.setBrightness(value);
        return true;
    }

    // TODO /eas reset
//        @Override
//        public Optional<Brightness> getResetValue() {
//            return Optional.empty();
//        }
}
