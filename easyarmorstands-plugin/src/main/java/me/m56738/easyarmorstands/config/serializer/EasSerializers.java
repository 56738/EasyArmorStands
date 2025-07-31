package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.api.util.Color;
import me.m56738.easyarmorstands.common.message.MessageStyle;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

import java.text.DecimalFormat;

public class EasSerializers {
    private static final TypeSerializerCollection SERIALIZERS = TypeSerializerCollection.builder()
            .register(Color.class, new ColorSerializer())
            .register(Component.class, new MiniMessageSerializer(MiniMessage.miniMessage()))
            .register(DecimalFormat.class, new DecimalFormatSerializer())
            .register(Key.class, new KeySerializer())
            .register(Material.class, new MaterialSerializer())
            .register(MessageStyle.class, new MessageStyleSerializer())
            .build();

    public static TypeSerializerCollection serializers() {
        return SERIALIZERS;
    }
}
