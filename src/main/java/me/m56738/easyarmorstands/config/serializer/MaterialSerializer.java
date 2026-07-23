package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.inventory.ItemType;
import net.kyori.adventure.key.Key;
import org.spongepowered.configurate.serialize.ScalarSerializer;
import org.spongepowered.configurate.serialize.SerializationException;

import java.lang.reflect.Type;
import java.util.function.Predicate;

public class MaterialSerializer extends ScalarSerializer<ItemType> {
    private final Platform platform;

    public MaterialSerializer(Platform platform) {
        super(ItemType.class);
        this.platform = platform;
    }

    @Override
    public ItemType deserialize(Type type, Object value) throws SerializationException {
        try {
            return platform.getItemType(Key.key(value.toString()));
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }

    @Override
    protected Object serialize(ItemType item, Predicate<Class<?>> typeSupported) {
        return item.key().asMinimalString();
    }
}
