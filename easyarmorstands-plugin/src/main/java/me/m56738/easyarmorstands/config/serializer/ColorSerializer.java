package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.lib.configurate.ConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.configurate.serialize.TypeSerializer;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.Type;

public class ColorSerializer implements TypeSerializer<Color> {
    @Override
    public Color deserialize(Type type, ConfigurationNode node) throws SerializationException {
        if (node.isNull()) {
            return null;
        }

        if (node.isMap()) {
            return Color.fromRGB(
                    node.node("red").getInt(),
                    node.node("green").getInt(),
                    node.node("blue").getInt());
        }

        String value = node.getString();
        if (value == null) {
            return null;
        }

        if (value.startsWith("#")) {
            try {
                return Color.fromRGB(Integer.parseInt(value.substring(1), 16));
            } catch (NumberFormatException e) {
                throw new SerializationException(e);
            }
        }

        try {
            return DyeColor.valueOf(value).getColor();
        } catch (IllegalArgumentException e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public void serialize(Type type, @Nullable Color obj, ConfigurationNode node) throws SerializationException {
        if (obj == null) {
            node.raw(null);
            return;
        }
        node.node("red").set(obj.getRed());
        node.node("green").set(obj.getGreen());
        node.node("blue").set(obj.getBlue());
    }
}
