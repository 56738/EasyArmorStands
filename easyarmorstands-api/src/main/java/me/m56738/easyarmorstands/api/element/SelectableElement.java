package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.node.Node;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface SelectableElement extends EditableElement, NamedElement {
    @Contract(pure = true)
    @NotNull Button createButton(@NotNull Session session);

    @Contract(pure = true)
    @NotNull Node createNode(@NotNull Session session);
}
