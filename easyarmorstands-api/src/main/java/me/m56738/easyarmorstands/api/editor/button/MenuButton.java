package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MenuButton {
    @NotNull Button getButton();

    @NotNull Component getName();

    default void onClick(@NotNull Session session, @Nullable Vector3dc cursor) {
    }

    default void onUpdate(@NotNull Session session, @Nullable Vector3dc cursor, @NotNull UpdateContext context) {
        context.addInput(new MenuButtonInput(session, this, cursor));
    }

    default boolean isAlwaysFocused() {
        return false;
    }
}
