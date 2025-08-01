package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.lib.configurate.CommentedConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.property.button.GravityToggleButton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class GravityPropertyType extends BooleanTogglePropertyType {
    private List<String> canTickWarning = Collections.emptyList();

    public GravityPropertyType(@NotNull Key key) {
        super(key);
    }

    @Override
    public void load(@NotNull CommentedConfigurationNode config) throws SerializationException {
        super.load(config);
        canTickWarning = config.node("can-tick-warning").getList(String.class);
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull Property<Boolean> property, @NotNull PropertyContainer container) {
        return new GravityToggleButton(property, container, buttonTemplate, canTickWarning);
    }
}
