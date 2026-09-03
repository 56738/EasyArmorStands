package me.m56738.easyarmorstands.config.serializer;

import org.bukkit.NamespacedKey;
import org.spongepowered.configurate.serialize.ScalarSerializer;
import org.spongepowered.configurate.serialize.SerializationException;

import java.lang.reflect.Type;
import java.util.function.Predicate;

public class NamespacedKeySerializer extends ScalarSerializer<NamespacedKey> {
    public NamespacedKeySerializer() {
        super(NamespacedKey.class);
    }

    @Override
    public NamespacedKey deserialize(Type type, Object value) throws SerializationException {
        return NamespacedKey.fromString(value.toString());
    }

    @Override
    protected Object serialize(NamespacedKey item, Predicate<Class<?>> typeSupported) {
        return item.asString();
    }
}
