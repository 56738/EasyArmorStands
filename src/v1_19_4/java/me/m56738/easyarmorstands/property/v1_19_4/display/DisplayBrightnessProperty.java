package me.m56738.easyarmorstands.property.v1_19_4.display;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.type.PropertyType;
import org.bukkit.entity.Display;
import org.bukkit.entity.Display.Brightness;
import org.jetbrains.annotations.Nullable;

public class DisplayBrightnessProperty implements Property<@Nullable Brightness> {
    private final Display entity;

    public DisplayBrightnessProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<@Nullable Brightness> getType() {
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
