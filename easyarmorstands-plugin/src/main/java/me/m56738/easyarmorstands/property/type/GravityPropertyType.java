package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.property.button.GravityToggleButton;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

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
    public @Nullable MenuSlot createSlot(@NotNull Element element) {
        return new GravityToggleButton(element, this, buttonTemplate, canTickWarning);
    }
}
