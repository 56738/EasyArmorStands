package me.m56738.easyarmorstands.property;

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
