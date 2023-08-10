package me.m56738.easyarmorstands.property.key;

import me.m56738.easyarmorstands.util.ArmorStandPart;

import java.util.Objects;

class ArmorStandPartKey<T> implements Key<T> {
    private final Key<T> key;
    private final ArmorStandPart part;

    ArmorStandPartKey(Key<T> key, ArmorStandPart part) {
        this.part = part;
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArmorStandPartKey<?> that = (ArmorStandPartKey<?>) o;
        return part == that.part && Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(part, key);
    }
}
