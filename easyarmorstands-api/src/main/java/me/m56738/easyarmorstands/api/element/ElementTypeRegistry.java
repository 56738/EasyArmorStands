package me.m56738.easyarmorstands.api.element;

import net.kyori.adventure.key.Key;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface ElementTypeRegistry {
    void register(Key key, ElementType type);

    @Nullable ElementType getOrNull(Key key);

    default ElementType get(Key key) {
        ElementType elementType = getOrNull(key);
        if (elementType == null) {
            throw new UnknownElementTypeException(key, null);
        }
        return elementType;
    }
}
