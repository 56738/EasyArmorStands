package me.m56738.easyarmorstands.history;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.PropertyAction;

import java.util.Objects;

public class ChangeKey<T> {
    private final Element element;
    private final PropertyType<T> propertyType;

    public ChangeKey(Element element, PropertyType<T> propertyType) {
        this.element = element;
        this.propertyType = propertyType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangeKey<?> changeKey = (ChangeKey<?>) o;
        return Objects.equals(element, changeKey.element) && Objects.equals(propertyType, changeKey.propertyType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, propertyType);
    }

    public Action createChangeAction(T oldValue, T value) {
        return new PropertyAction<>(element.getReference(), propertyType, oldValue, value);
    }
}
