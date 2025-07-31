package me.m56738.easyarmorstands.api.property.type;

import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

@NullMarked
class DefaultValueRenderer<T> implements ValueRenderer<T> {
    @Override
    public String renderString(T value) {
        return String.valueOf(value);
    }

    @Override
    public Component renderComponent(T value) {
        return Component.text(renderString(value));
    }
}
