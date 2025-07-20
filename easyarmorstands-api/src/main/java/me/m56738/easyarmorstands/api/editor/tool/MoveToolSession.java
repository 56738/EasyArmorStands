package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public interface MoveToolSession extends ToolSession {
    void setChange(@NotNull Vector3dc change);

    @Contract(pure = true)
    void snapChange(@NotNull Vector3d change, @NotNull Snapper context);

    @Contract(pure = true)
    @NotNull Vector3dc getPosition();

    void setPosition(@NotNull Vector3dc position);

    default boolean canSetPosition(Player player) {
        return true;
    }
}
