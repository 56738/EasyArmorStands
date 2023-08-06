package me.m56738.easyarmorstands.property.key;

import me.m56738.easyarmorstands.util.ArmorStandPart;

public interface Key<T> {
    static <T> Key<T> of(Class<T> type) {
        return new TypeKey<>(type);
    }

    static <T> Key<T> of(Class<T> type, ArmorStandPart part) {
        return Key.of(type).withPart(part);
    }

    default Key<T> withPart(ArmorStandPart part) {
        return new ArmorStandPartKey<>(part, this);
    }
}
