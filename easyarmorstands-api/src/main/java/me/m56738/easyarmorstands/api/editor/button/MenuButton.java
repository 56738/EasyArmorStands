package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.input.Input;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

import java.util.List;

public interface MenuButton {
    @NotNull Button getButton();

    @NotNull Component getName();

    default void onClick(@NotNull Session session, @Nullable Vector3dc cursor) {
    }

    default void collectInputs(@NotNull Session session, @Nullable Vector3dc cursor, @NotNull List<@NotNull Input> inputs) {
        inputs.add(new MenuButtonInput(session, this, cursor));
    }

    default boolean isSelected() {
        return false;
    }
}
