package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.platform.entity.Entity;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

@NullMarked
public class EntityComponentProperty implements Property<Component> {
    private static final MiniMessage serializer = EasyArmorStandsCommon.miniMessage();
    private static final MiniMessage nonVirtualSerializer = MiniMessage.builder()
            .emitVirtuals(false)
            .build();
    private final PropertyType<Component> type;
    private final Key key;
    private final Entity entity;
    private final Supplier<Component> getter;
    private final Consumer<Component> setter;

    public EntityComponentProperty(PropertyType<Component> type, Entity entity, Supplier<Component> getter, Consumer<Component> setter) {
        this.type = type;
        this.key = type.key();
        this.entity = entity;
        this.getter = getter;
        this.setter = setter;
    }

    public static Component resolveValue(Entity entity, Key key, Component realText) {
        String miniMessageText = entity.getCustomDataString(key);
        if (miniMessageText != null) {
            if (nonVirtualSerializer.deserialize(miniMessageText).equals(realText.compact())) {
                return serializer.deserialize(miniMessageText);
            }
        }
        return realText;
    }

    public static void saveValue(Entity entity, Key key, @Nullable Component value) {
        if (value != null) {
            entity.setCustomDataString(key, serializer.serialize(value));
        } else {
            entity.removeCustomData(key);
        }
    }

    @Override
    public PropertyType<Component> getType() {
        return type;
    }

    @Override
    public Component getValue() {
        return resolveValue(entity, key, getter.get());
    }

    @Override
    public boolean setValue(Component value) {
        setter.accept(value);
        saveValue(entity, key, value);
        return true;
    }
}
