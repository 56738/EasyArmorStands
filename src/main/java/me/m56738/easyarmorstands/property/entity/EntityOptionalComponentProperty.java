package me.m56738.easyarmorstands.property.entity;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@NullMarked
public class EntityOptionalComponentProperty implements Property<Optional<Component>> {
    private final PropertyType<Optional<Component>> type;
    private final NamespacedKey key;
    private final Entity entity;
    private final Supplier<@Nullable Component> getter;
    private final Consumer<@Nullable Component> setter;

    public EntityOptionalComponentProperty(PropertyType<Optional<Component>> type, Entity entity, Supplier<@Nullable Component> getter, Consumer<@Nullable Component> setter) {
        this.type = type;
        this.key = new NamespacedKey(type.key().namespace(), type.key().value());
        this.entity = entity;
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public PropertyType<Optional<Component>> getType() {
        return type;
    }

    @Override
    public Optional<Component> getValue() {
        Component realText = getter.get();
        if (realText == null) {
            return Optional.empty();
        }
        return Optional.of(EntityComponentProperty.resolveValue(entity, key, realText));
    }

    @Override
    public boolean setValue(Optional<Component> value) {
        setter.accept(value.orElse(null));
        EntityComponentProperty.saveValue(entity, key, value.orElse(null));
        return true;
    }
}
