package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.api.menu.MenuSlotType;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.config.serializer.PropertyTypeSerializer;
import me.m56738.easyarmorstands.lib.configurate.ConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.property.type.MenuPropertyType;
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
        PropertyType<?> type = node.node("property").require(PropertyTypeSerializer.TYPE);
        if (type instanceof MenuPropertyType<?> menuPropertyType) {
            return new PropertySlotFactory<>(menuPropertyType);
        } else {
            throw new SerializationException(type.key() + " cannot be used in a menu");
        }
    }
}
