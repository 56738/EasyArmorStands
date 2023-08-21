package me.m56738.easyarmorstands.api.property;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class UnknownPropertyTypeException extends RuntimeException {
    private final @NotNull Key key;
    private final @NotNull Class<?> type;

    public UnknownPropertyTypeException(@NotNull Key key, @NotNull Class<?> type) {
        super(key.asString());
        this.key = key;
        this.type = type;
    }

    public @NotNull Key getKey() {
        return key;
    }

    public @NotNull Class<?> getType() {
        return type;
    }
}
