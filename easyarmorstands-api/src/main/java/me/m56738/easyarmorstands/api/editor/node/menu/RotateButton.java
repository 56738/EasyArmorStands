package me.m56738.easyarmorstands.api.editor.node.menu;

import me.m56738.easyarmorstands.api.editor.node.Node;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface RotateButton extends MenuButton {
    @NotNull Node createNode();
}
