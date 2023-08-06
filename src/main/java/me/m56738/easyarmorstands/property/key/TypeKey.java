package me.m56738.easyarmorstands.property.key;

import java.util.Objects;

class TypeKey<T> implements Key<T> {
    private final Class<T> type;

    TypeKey(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeKey<?> that = (TypeKey<?>) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
