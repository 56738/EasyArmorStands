package me.m56738.easyarmorstands.api.editor.layer;

import me.m56738.easyarmorstands.api.element.Element;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ElementLayer extends Layer {
    @Contract(pure = true)
    @NotNull Element getElement();
}
