package me.m56738.easyarmorstands.property.v1_19_4.display;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.property.ResettableEntityProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DisplayBrightnessProperty implements ResettableEntityProperty<Display, Optional<Display.Brightness>> {
    @Override
    public Optional<Display.Brightness> getValue(Display entity) {
        return Optional.ofNullable(entity.getBrightness());
    }

    @Override
    public TypeToken<Optional<Display.Brightness>> getValueType() {
        return new TypeToken<Optional<Display.Brightness>>() {
        };
    }

    @Override
    public void setValue(Display entity, Optional<Display.Brightness> value) {
        entity.setBrightness(value.orElse(null));
    }

    @Override
    public @NotNull String getName() {
        return "brightness";
    }

    @Override
    public @NotNull Class<Display> getEntityType() {
        return Display.class;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.text("brightness");
    }

    @Override
    public @NotNull Component getValueName(Optional<Display.Brightness> value) {
        if (value.isPresent()) {
            Display.Brightness brightness = value.get();
            return Component.text()
                    .content("Block: ")
                    .append(Component.text(brightness.getBlockLight()))
                    .append(Component.text(", "))
                    .append(Component.text("Sky: "))
                    .append(Component.text(brightness.getSkyLight()))
                    .build();
        } else {
            return Component.text("default");
        }
    }

    @Override
    public @Nullable String getPermission() {
        return "easyarmorstands.property.display.brightness";
    }

    @Override
    public Optional<Display.Brightness> getResetValue() {
        return Optional.empty();
    }
}
