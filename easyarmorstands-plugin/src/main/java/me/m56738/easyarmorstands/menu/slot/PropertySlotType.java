package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.lib.configurate.ConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class PropertySlotType implements MenuSlotType {
    public static final Key KEY = Key.key("easyarmorstands", "property");

    @Override
    public @NotNull Key key() {
        return KEY;
    }

    @Override
    public @NotNull MenuSlotFactory load(@NotNull ConfigurationNode node) throws SerializationException {
        PropertyType<?> type = node.node("property").get(new TypeToken<>() {
        });
        return new PropertySlotFactory<>(type);
    }
}
