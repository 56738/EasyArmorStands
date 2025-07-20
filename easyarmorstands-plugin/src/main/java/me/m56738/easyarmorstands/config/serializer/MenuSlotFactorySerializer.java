package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.menu.slot.MenuSlotType;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class MenuSlotFactorySerializer implements TypeSerializer<MenuSlotFactory> {
    @Override
    public MenuSlotFactory deserialize(Type type, ConfigurationNode node) throws SerializationException {
        MenuSlotType slotType = node.node("type").get(MenuSlotType.class);
        if (slotType == null) {
            return null;
        }
        return slotType.load(node.node("config"));
    }

    @Override
    public void serialize(Type type, @Nullable MenuSlotFactory obj, ConfigurationNode node) throws SerializationException {
        throw new SerializationException(new UnsupportedOperationException());
    }
}
