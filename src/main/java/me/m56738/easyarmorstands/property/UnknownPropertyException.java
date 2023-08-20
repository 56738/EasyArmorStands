package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.property.type.PropertyType;

public class UnknownPropertyException extends RuntimeException {
    private final PropertyType<?> type;

    public UnknownPropertyException(PropertyType<?> type) {
        super(type.toString());
        this.type = type;
    }

    public PropertyType<?> getType() {
        return type;
    }
}
