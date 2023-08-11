package me.m56738.easyarmorstands.property.key;

import net.kyori.adventure.key.Key;

import java.util.Objects;

class SimplePropertyKey<T> implements PropertyKey<T> {
    private final Key key;

    SimplePropertyKey(Key key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key.asString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimplePropertyKey<?> that = (SimplePropertyKey<?>) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
