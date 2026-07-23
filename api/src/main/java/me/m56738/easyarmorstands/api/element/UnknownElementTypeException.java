package me.m56738.easyarmorstands.api.element;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class UnknownElementTypeException extends RuntimeException {
    private final @NotNull Key key;

    public UnknownElementTypeException(@NotNull Key key) {
        super(key.asString());
        this.key = key;
    }

    public @NotNull Key getKey() {
        return key;
    }
}
