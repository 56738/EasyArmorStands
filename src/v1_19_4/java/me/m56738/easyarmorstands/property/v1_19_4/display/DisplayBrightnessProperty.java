package me.m56738.easyarmorstands.property.v1_19_4.display;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.bukkit.entity.Display.Brightness;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DisplayBrightnessProperty implements Property<@Nullable Brightness> {
    public static final PropertyType<@Nullable Brightness> TYPE = new Type();
    private final Display entity;

    public DisplayBrightnessProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<@Nullable Brightness> getType() {
        return TYPE;
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

    private static class Type implements PropertyType<@Nullable Brightness> {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.display.brightness";
        }

        @Override
        public @NotNull Component getDisplayName() {
            return Component.text("brightness");
        }

        @Override
        public @NotNull Component getValueComponent(@Nullable Brightness value) {
            if (value != null) {
                return Component.text()
                        .content("Block: ")
                        .append(Component.text(value.getBlockLight()))
                        .append(Component.text(", "))
                        .append(Component.text("Sky: "))
                        .append(Component.text(value.getSkyLight()))
                        .build();
            } else {
                return Component.text("default");
            }
        }

        // TODO /eas reset
//        @Override
//        public Optional<Brightness> getResetValue() {
//            return Optional.empty();
//        }
    }
}
