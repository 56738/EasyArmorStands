package me.m56738.easyarmorstands.api.property;

import io.leangen.geantyref.TypeToken;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UnknownPropertyTypeException extends RuntimeException {
    private final @NotNull Key key;
    private final @Nullable TypeToken<?> type;

    public UnknownPropertyTypeException(@NotNull Key key, @Nullable TypeToken<?> type) {
        super(key.asString() + (type != null ? " " + type.getAnnotatedType() : ""));
        this.key = key;
        this.type = type;
    }

    public @NotNull Key getKey() {
        return key;
    }

    public @Nullable TypeToken<?> getType() {
        return type;
    }
}
