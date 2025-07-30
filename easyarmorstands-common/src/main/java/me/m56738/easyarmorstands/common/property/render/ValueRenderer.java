package me.m56738.easyarmorstands.common.property.render;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

@FunctionalInterface
public interface ValueRenderer<T> {
    Component renderComponent(T value);

    default String renderString(T value) {
        return PlainTextComponentSerializer.plainText().serialize(renderComponent(value));
    }
}
