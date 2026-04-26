package me.m56738.easyarmorstands.api.editor.button;

import me.m56738.easyarmorstands.api.editor.layer.Layer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface AxisMoveButton extends MenuButton {
    @NotNull Layer createLayer();
}
