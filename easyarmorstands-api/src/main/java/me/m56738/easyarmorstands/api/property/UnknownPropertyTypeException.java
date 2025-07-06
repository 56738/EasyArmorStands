package me.m56738.easyarmorstands.api.property;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class UnknownPropertyTypeException extends RuntimeException {
    private final @NotNull Key key;

    public UnknownPropertyTypeException(@NotNull Key key) {
        super(key.asString());
        this.key = key;
    }

    public @NotNull Key getKey() {
        return key;
    }
}
