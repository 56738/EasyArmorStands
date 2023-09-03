package me.m56738.easyarmorstands.config.serializer;

import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.menu.MenuSlotType;
import me.m56738.easyarmorstands.api.menu.MenuSlotTypeRegistry;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class MenuSlotTypeSerializer implements TypeSerializer<MenuSlotType> {
    private final MenuSlotTypeRegistry registry;

    public MenuSlotTypeSerializer() {
        this(EasyArmorStands.get().menuSlotTypeRegistry());
    }

    public MenuSlotTypeSerializer(MenuSlotTypeRegistry registry) {
        this.registry = registry;
    }

    @Override
    public MenuSlotType deserialize(Type type, ConfigurationNode node) throws SerializationException {
        Key key = node.get(Key.class);
        if (key == null) {
            return null;
        }
        MenuSlotType slotType = registry.getOrNull(key);
        if (slotType == null) {
            throw new SerializationException("Unknown menu slot type: " + key.asString());
        }
        return slotType;
    }

    @Override
    public void serialize(Type type, @Nullable MenuSlotType obj, ConfigurationNode node) throws SerializationException {
        if (obj == null) {
            node.raw(null);
            return;
        }
        node.set(obj.key());
    }
}
