package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.lib.configurate.ConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.configurate.serialize.TypeSerializer;
import me.m56738.easyarmorstands.message.MessageStyle;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.Type;
import java.util.Locale;

public class MessageStyleSerializer implements TypeSerializer<MessageStyle> {
    @Override
    public MessageStyle deserialize(Type type, ConfigurationNode node) throws SerializationException {
        String value = node.getString();
        if (value == null) {
            return null;
        }

        value = value.replace('-', '_').toUpperCase(Locale.ROOT);

        try {
            return MessageStyle.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public void serialize(Type type, @Nullable MessageStyle style, ConfigurationNode node) throws SerializationException {
        if (style != null) {
            node.raw(style.name().replace('_', '-').toLowerCase(Locale.ROOT));
        } else {
            node.raw(null);
        }
    }
}
