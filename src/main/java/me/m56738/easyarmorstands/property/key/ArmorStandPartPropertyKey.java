package me.m56738.easyarmorstands.property.key;

import me.m56738.easyarmorstands.util.ArmorStandPart;
import net.kyori.adventure.key.Key;

import java.util.Objects;

class ArmorStandPartPropertyKey<T> implements PropertyKey<T> {
    private final Key key;
    private final ArmorStandPart part;

    ArmorStandPartPropertyKey(Key key, ArmorStandPart part) {
        this.key = key;
        this.part = part;
    }

    @Override
    public String toString() {
        return key.asString() + "." + part.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArmorStandPartPropertyKey<?> that = (ArmorStandPartPropertyKey<?>) o;
        return Objects.equals(key, that.key) && part == that.part;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, part);
    }
}
