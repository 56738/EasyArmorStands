package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.lib.configurate.serialize.ScalarSerializer;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.function.Predicate;

public class DecimalFormatSerializer extends ScalarSerializer<DecimalFormat> {
    public DecimalFormatSerializer() {
        super(DecimalFormat.class);
    }

    @Override
    public DecimalFormat deserialize(Type type, Object obj) throws SerializationException {
        return new DecimalFormat(obj.toString());
    }

    @Override
    protected Object serialize(DecimalFormat item, Predicate<Class<?>> typeSupported) {
        return item.toPattern();
    }
}
