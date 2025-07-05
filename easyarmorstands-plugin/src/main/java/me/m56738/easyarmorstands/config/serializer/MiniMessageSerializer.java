package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.lib.configurate.serialize.ScalarSerializer;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.MiniMessage;

import java.lang.reflect.Type;
import java.util.function.Predicate;

public class MiniMessageSerializer extends ScalarSerializer<Component> {
    private final MiniMessage miniMessage;

    public MiniMessageSerializer(MiniMessage miniMessage) {
        super(Component.class);
        this.miniMessage = miniMessage;
    }

    @Override
    public Component deserialize(Type type, Object obj) throws SerializationException {
        return miniMessage.deserialize(obj.toString());
    }

    @Override
    protected Object serialize(Component item, Predicate<Class<?>> typeSupported) {
        return miniMessage.serialize(item);
    }
}
