package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.util.ItemTemplate;
import me.m56738.easyarmorstands.lib.configurate.serialize.TypeSerializerCollection;
import me.m56738.easyarmorstands.message.MessageStyle;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Color;
import org.bukkit.Material;

import java.text.DecimalFormat;

public class EasSerializers {
    private static final TypeSerializerCollection SERIALIZERS = TypeSerializerCollection.builder()
            .register(Color.class, new ColorSerializer())
            .register(Component.class, new MiniMessageSerializer(MiniMessage.miniMessage()))
            .register(DecimalFormat.class, new DecimalFormatSerializer())
            .register(ItemTemplate.class, new ItemTemplateSerializer())
            .register(Key.class, new KeySerializer())
            .register(Material.class, new MaterialSerializer())
            .register(PropertyType.type(), new PropertyTypeSerializer())
            .register(MessageStyle.class, new MessageStyleSerializer())
            .build();

    public static TypeSerializerCollection serializers() {
        return SERIALIZERS;
    }
}
