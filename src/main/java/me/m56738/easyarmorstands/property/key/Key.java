package me.m56738.easyarmorstands.property.key;

import me.m56738.easyarmorstands.util.ArmorStandPart;

public interface Key<T> {
    static <T> Key<T> of(Class<T> type) {
        return new TypeKey<>(type);
    }

    static <T> Key<T> of(Key<T> key, ArmorStandPart part) {
        return new ArmorStandPartKey<>(key, part);
    }
}
