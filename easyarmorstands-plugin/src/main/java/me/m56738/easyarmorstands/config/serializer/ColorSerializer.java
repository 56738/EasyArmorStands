package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.api.util.Color;
import me.m56738.easyarmorstands.paper.api.platform.adapter.PaperColorAdapter;
import org.bukkit.DyeColor;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class ColorSerializer implements TypeSerializer<Color> {
    @Override
    public Color deserialize(Type type, ConfigurationNode node) throws SerializationException {
        if (node.isNull()) {
            return null;
        }

        if (node.isMap()) {
            return Color.of(
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
                return Color.ofRGB(Integer.parseInt(value.substring(1), 16));
            } catch (NumberFormatException e) {
                throw new SerializationException(e);
            }
        }

        try {
            return PaperColorAdapter.fromNative(DyeColor.valueOf(value).getColor());
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
        node.node("red").set(obj.red());
        node.node("green").set(obj.green());
        node.node("blue").set(obj.blue());
    }
}
