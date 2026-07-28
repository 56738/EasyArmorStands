package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.lib.configurate.serialize.TypeSerializerCollection;
import me.m56738.easyarmorstands.message.MessageStyle;
import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.inventory.ItemType;
import me.m56738.easyarmorstands.util.ItemTemplate;
import net.kyori.adventure.text.Component;

public class EasSerializers {
    public static TypeSerializerCollection serializers(Platform platform) {
        return TypeSerializerCollection.builder()
                .register(Component.class, new MiniMessageSerializer(EasyArmorStandsCommon.miniMessage()))
                .register(ItemTemplate.class, new ItemTemplateSerializer(platform))
                .register(ItemType.class, new ItemTypeSerializer(platform))
                .register(MessageStyle.class, new MessageStyleSerializer())
                .build();
    }
}
