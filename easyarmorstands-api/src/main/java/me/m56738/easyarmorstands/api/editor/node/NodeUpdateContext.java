package me.m56738.easyarmorstands.api.editor.node;

import me.m56738.easyarmorstands.api.editor.button.MenuButton;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface NodeUpdateContext {
    void addButton(MenuButton button);

    void removeButton(MenuButton button);
}
