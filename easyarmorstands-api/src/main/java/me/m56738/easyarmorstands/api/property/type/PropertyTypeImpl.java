package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.property.renderer.ValueRenderer;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record PropertyTypeImpl<T>(
        Key key,
        Component name,
        @Nullable String permission,
        ValueRenderer<T> renderer
) implements PropertyType<T> {
    @Override
    public Key key() {
        return key;
    }

    @Override
    public Component name() {
        return name;
    }

    @Override
    public @Nullable String permission() {
        return permission;
    }

    @Override
    public Component getValueComponent(T value) {
        return renderer.renderComponent(value);
    }

    @Override
    public String getValueString(T value) {
        return renderer.renderString(value);
    }
}
