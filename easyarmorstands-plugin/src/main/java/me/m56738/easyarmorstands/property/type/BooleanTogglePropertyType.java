package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.property.button.BooleanToggleButton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BooleanTogglePropertyType extends BooleanPropertyType {
    public BooleanTogglePropertyType(@NotNull Key key) {
        super(key);
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull Property<Boolean> property, @NotNull PropertyContainer container) {
        return new BooleanToggleButton(property, container, buttonTemplate);
    }
}
