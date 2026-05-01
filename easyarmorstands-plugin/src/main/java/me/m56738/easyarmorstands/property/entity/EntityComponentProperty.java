package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

@NullMarked
public class EntityComponentProperty implements Property<Component> {
    private final PropertyType<Component> type;
    private final NamespacedKey key;
    private final Entity entity;
    private final Supplier<Component> getter;
    private final Consumer<Component> setter;
    private static final MiniMessage serializer = EasyArmorStandsPlugin.getInstance().getMiniMessage();
    private static final MiniMessage nonVirtualSerializer = MiniMessage.builder()
            .emitVirtuals(false)
            .build();

    public EntityComponentProperty(PropertyType<Component> type, Entity entity, Supplier<Component> getter, Consumer<Component> setter) {
        this.type = type;
        this.key = new NamespacedKey(type.key().namespace(), type.key().value());
        this.entity = entity;
        this.getter = getter;
        this.setter = setter;
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

    public static Component resolveValue(Entity entity, NamespacedKey key, Component realText) {
        String miniMessageText = entity.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (miniMessageText != null) {
            if (nonVirtualSerializer.deserialize(miniMessageText).equals(realText.compact())) {
                return serializer.deserialize(miniMessageText);
            }
        }
        return realText;
    }

    public static void saveValue(Entity entity, NamespacedKey key, @Nullable Component value) {
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        if (value != null) {
            pdc.set(key, PersistentDataType.STRING, serializer.serialize(value));
        } else {
            pdc.remove(key);
        }
    }
}
