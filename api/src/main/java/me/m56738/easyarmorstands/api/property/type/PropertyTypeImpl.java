package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.formatter.StringFormatter;
import me.m56738.easyarmorstands.api.formatter.ValueFormatter;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
record PropertyTypeImpl<T>(
        Key key,
        Component name,
        ValueFormatter<T> formatter,
        @Nullable String permission
) implements PropertyType<T> {
    @Override
    public Component getName() {
        return name;
    }

    @Override
    public @Nullable String getPermission() {
        return permission;
    }

    @Override
    public Component getValueComponent(T value) {
        return formatter.format(value);
    }

    @Override
    public String getValueString(T value) {
        return formatter.formatAsString(value);
    }

    static class Builder<T> implements PropertyType.Builder<T> {
        private final Key key;
        private Component name;
        private ValueFormatter<T> formatter = new StringFormatter<>();
        private @Nullable String permission;

        public Builder(Key key) {
            this.key = key;
            this.name = Component.text(key.asString());
        }

        @Override
        public Builder<T> name(Component name) {
            this.name = Objects.requireNonNull(name);
            return this;
        }

        @Override
        public Builder<T> formatter(ValueFormatter<T> formatter) {
            this.formatter = Objects.requireNonNull(formatter);
            return this;
        }

        @Override
        public Builder<T> permission(String permission) {
            this.permission = Objects.requireNonNull(permission);
            return this;
        }

        @Override
        public PropertyType<T> build() {
            return new PropertyTypeImpl<>(key, name, formatter, permission);
        }
    }
}
