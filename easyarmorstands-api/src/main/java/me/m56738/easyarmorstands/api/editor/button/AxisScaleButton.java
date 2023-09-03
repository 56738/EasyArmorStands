package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.node.Node;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface AxisScaleButton extends MenuButton {
    @NotNull Node createNode();
}
