package me.m56738.easyarmorstands.api.property;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UnknownPropertyTypeException extends RuntimeException {
    private final @NotNull Key key;
    private final @Nullable Class<?> type;

    public UnknownPropertyTypeException(@NotNull Key key, @Nullable Class<?> type) {
        super(key.asString());
        this.key = key;
        this.type = type;
    }

    public @NotNull Key getKey() {
        return key;
    }

    public @Nullable Class<?> getType() {
        return type;
    }
}
