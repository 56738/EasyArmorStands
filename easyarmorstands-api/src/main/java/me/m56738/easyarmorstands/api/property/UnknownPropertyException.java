package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jetbrains.annotations.NotNull;

public class UnknownPropertyException extends RuntimeException {
    private final @NotNull PropertyType<?> type;

    public UnknownPropertyException(@NotNull PropertyType<?> type) {
        super(type.toString());
        this.type = type;
    }

    public @NotNull PropertyType<?> getType() {
        return type;
    }
}
