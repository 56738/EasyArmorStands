package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.api.menu.MenuSlotType;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import static me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry.propertyTypeRegistry;

public class PropertySlotType implements MenuSlotType {
    public static final Key KEY = Key.key("easyarmorstands", "property");

    @Override
    public @NotNull Key key() {
        return KEY;
    }

    @Override
    public @NotNull MenuSlotFactory load(ConfigurationNode node) throws SerializationException {
        PropertyType<?> type = propertyTypeRegistry().get(node.node("property").get(Key.class));
        return new PropertySlotFactory<>(type);
    }
}
