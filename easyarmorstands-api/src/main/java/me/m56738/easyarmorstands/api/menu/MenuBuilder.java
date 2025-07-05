package me.m56738.easyarmorstands.api.menu;

import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

public interface MenuBuilder {
    MenuAction addAction(Component label, @Nullable Runnable action);

    MenuTextInput addTextInput(Component label, String value);

    MenuBooleanInput addBooleanInput(Component label, boolean value);

    MenuFloatInput addFloatInput(Component label, float min, float max, float value);

    Menu build();
}
