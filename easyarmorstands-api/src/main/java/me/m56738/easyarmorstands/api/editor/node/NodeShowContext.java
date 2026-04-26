package me.m56738.easyarmorstands.api.editor.node;

import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface NodeShowContext {
    void addButton(MenuButton button);
}
