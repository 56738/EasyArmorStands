package me.m56738.easyarmorstands.api.editor.tool;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Tool<S extends ToolSession> {
    @NotNull S start();

    @Contract(pure = true)
    default boolean canUse(@NotNull Player player) {
        return true;
    }
}
