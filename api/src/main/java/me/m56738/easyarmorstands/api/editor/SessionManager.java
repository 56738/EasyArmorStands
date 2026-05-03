package me.m56738.easyarmorstands.api.editor;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SessionManager {
    @Contract(pure = true)
    @Nullable Session getSession(@NotNull Player player);

    @NotNull Session startSession(@NotNull Player player);

    void stopSession(@NotNull Session session);
}
