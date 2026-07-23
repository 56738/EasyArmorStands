package me.m56738.easyarmorstands.registry;

import net.kyori.adventure.key.Key;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public final class RegistryUtil {
    private RegistryUtil() {
    }

    public static void validate(Class<?> holder, Consumer<Key> lookup) {
        for (Field field : holder.getFields()) {
            Key key;
            try {
                key = (Key) field.get(null);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            lookup.accept(key);
        }
    }
}
