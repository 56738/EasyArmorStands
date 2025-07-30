package me.m56738.easyarmorstands.common.property.type;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.common.property.render.ValueRenderer;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
public class PropertyTypeBuilder<T> {
    private @Nullable Key key;
    private @Nullable Component name;
    private @Nullable String permission;
    private @Nullable ValueRenderer<T> renderer;

    public PropertyTypeBuilder<T> key(Key key) {
        this.key = key;
        return this;
    }

    public PropertyTypeBuilder<T> name(Component name) {
        this.name = name;
        return this;
    }

    public PropertyTypeBuilder<T> permission(String permission) {
        this.permission = permission;
        return this;
    }

    public PropertyTypeBuilder<T> renderer(ValueRenderer<T> renderer) {
        this.renderer = renderer;
        return this;
    }

    public PropertyType<T> build() {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(renderer, "renderer");
        return new PropertyTypeImpl<>(key, name, permission, renderer);
    }
}
