package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.lib.configurate.CommentedConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

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
}
