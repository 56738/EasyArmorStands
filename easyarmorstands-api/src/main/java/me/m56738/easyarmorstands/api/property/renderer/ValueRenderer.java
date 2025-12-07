package me.m56738.easyarmorstands.api.property.renderer;

import me.m56738.easyarmorstands.api.platform.profile.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

@FunctionalInterface
public interface ValueRenderer<T> {
    static <T> ValueRenderer<T> string() {
        return new DefaultValueRenderer<>();
    }

    static ValueRenderer<Profile> profile() {
        return new ProfileValueRenderer();
    }

    Component renderComponent(T value);

    default String renderString(T value) {
        return PlainTextComponentSerializer.plainText().serialize(renderComponent(value));
    }
}
