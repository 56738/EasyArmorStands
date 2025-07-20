package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.property.UnknownPropertyTypeException;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class PropertyTypeSerializer implements TypeSerializer<PropertyType<?>> {
    @Override
    public PropertyType<?> deserialize(Type type, ConfigurationNode node) throws SerializationException {
        Key key = node.get(Key.class);
        if (key == null) {
            return null;
        }
        try {
            return EasyArmorStands.get().propertyTypeRegistry().get(key);
        } catch (UnknownPropertyTypeException e) {
            throw new SerializationException("Unknown property type: " + key.asString());
        }
    }

    @Override
    public void serialize(Type type, @Nullable PropertyType<?> obj, ConfigurationNode node) throws SerializationException {
        if (obj == null) {
            node.raw(null);
            return;
        }
        node.set(Key.class, obj.key());
    }
}
