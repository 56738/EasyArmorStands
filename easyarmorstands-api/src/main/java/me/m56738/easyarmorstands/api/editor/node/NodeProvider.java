package me.m56738.easyarmorstands.api.editor.node;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface NodeProvider {
    @Contract(pure = true)
    @NotNull ElementSelectionNode elementSelectionNode();
}
