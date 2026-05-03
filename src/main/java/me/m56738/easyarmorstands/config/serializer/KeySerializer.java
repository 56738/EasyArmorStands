package me.m56738.easyarmorstands.config.serializer;

import net.kyori.adventure.key.Key;
import org.spongepowered.configurate.serialize.ScalarSerializer;
import org.spongepowered.configurate.serialize.SerializationException;

import java.lang.reflect.Type;
import java.util.function.Predicate;

@SuppressWarnings("PatternValidation")
public class KeySerializer extends ScalarSerializer<Key> {
    public KeySerializer() {
        super(Key.class);
    }

    @Override
    public Key deserialize(Type type, Object value) throws SerializationException {
        return Key.key(value.toString());
    }

    @Override
    protected Object serialize(Key item, Predicate<Class<?>> typeSupported) {
        return item.asString();
    }
}
