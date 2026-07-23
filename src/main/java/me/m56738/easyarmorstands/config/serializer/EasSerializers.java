package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.message.MessageStyle;
import me.m56738.easyarmorstands.platform.color.RGBColor;
import me.m56738.easyarmorstands.platform.inventory.ItemType;
import me.m56738.easyarmorstands.util.ItemTemplate;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

import java.text.DecimalFormat;

public class EasSerializers {
    public static TypeSerializerCollection serializers(EasyArmorStandsCommon eas) {
        return TypeSerializerCollection.builder()
                .register(RGBColor.class, new ColorSerializer())
                .register(Component.class, new MiniMessageSerializer(EasyArmorStandsCommon.miniMessage()))
                .register(DecimalFormat.class, new DecimalFormatSerializer())
                .register(ItemTemplate.class, new ItemTemplateSerializer(eas.platform()))
                .register(Key.class, new KeySerializer())
                .register(ItemType.class, new MaterialSerializer(eas.platform()))
                .register(PropertyTypeSerializer.TYPE, new PropertyTypeSerializer(eas))
                .register(MessageStyle.class, new MessageStyleSerializer())
                .build();
    }
}
