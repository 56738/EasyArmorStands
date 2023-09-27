package me.m56738.easyarmorstands.api.editor.node;

import me.m56738.easyarmorstands.api.element.Element;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ElementNode extends Node {
    @Contract(pure = true)
    @NotNull Element getElement();
}
