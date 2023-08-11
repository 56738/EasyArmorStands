package me.m56738.easyarmorstands.property.v1_19_4.display;

import me.m56738.easyarmorstands.property.PropertyType;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

class DisplaySizePropertyType implements PropertyType<Float> {
    private final Component displayName;

    DisplaySizePropertyType(Component displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getPermission() {
        return "easyarmorstands.property.display.size";
    }

    @Override
    public Component getDisplayName() {
        return displayName;
    }

    @Override
    public @NotNull Component getValueComponent(Float value) {
        return Component.text(value);
    }
}
