package me.m56738.easyarmorstands.api.editor.button;

import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface ButtonHandler {
    void onUpdate(ButtonHandlerContext context);

    Component getName();

    default boolean isSelected() {
        return false;
    }
}
