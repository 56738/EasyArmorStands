package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public interface MenuButton {
    @NotNull Button getButton();

    @NotNull Component getName();

    void onClick(@NotNull Session session, @Nullable Vector3dc cursor);

    @ApiStatus.Internal
    default boolean isHighlighted() {
        return false;
    }
}
