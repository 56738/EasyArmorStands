package me.m56738.easyarmorstands.config.serializer;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.api.menu.MenuFactory;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import me.m56738.easyarmorstands.api.util.ItemTemplate;
import me.m56738.easyarmorstands.menu.slot.MenuSlotType;
import me.m56738.easyarmorstands.common.message.MessageStyle;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Color;
import org.bukkit.Material;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

import java.text.DecimalFormat;

public class EasSerializers {
    private static final TypeSerializerCollection SERIALIZERS = TypeSerializerCollection.builder()
            .register(Color.class, new ColorSerializer())
            .register(Component.class, new MiniMessageSerializer(MiniMessage.miniMessage()))
            .register(DecimalFormat.class, new DecimalFormatSerializer())
            .register(ItemTemplate.class, new ItemTemplateSerializer())
            .register(Key.class, new KeySerializer())
            .register(Material.class, new MaterialSerializer())
            .register(MenuFactory.class, new MenuFactorySerializer())
            .register(MenuSlotFactory.class, new MenuSlotFactorySerializer())
            .register(MenuSlotType.class, new MenuSlotTypeSerializer())
            .register(new TypeToken<>() {
            }, new PropertyTypeSerializer())
            .register(MessageStyle.class, new MessageStyleSerializer())
            .build();

    public static TypeSerializerCollection serializers() {
        return SERIALIZERS;
    }
}
