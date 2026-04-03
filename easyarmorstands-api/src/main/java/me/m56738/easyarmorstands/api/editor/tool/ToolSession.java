package me.m56738.easyarmorstands.api.editor.tool;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public interface ToolSession {
    void revert();

    void commit(@Nullable Component description);

    @Contract(pure = true)
    boolean isValid();

    @Contract(pure = true)
    default @Nullable Component getStatus() {
        return null;
    }

    @Contract(pure = true)
    @Nullable Component getDescription();
}
