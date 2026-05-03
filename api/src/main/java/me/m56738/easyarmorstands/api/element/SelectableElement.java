package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.editor.node.Node;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An element which can be selected in the editor.
 */
public interface SelectableElement extends EditableElement, NamedElement {
    /**
     * Creates a button which allows selecting the element.
     *
     * @param session the session
     * @return a button
     */
    @Contract(pure = true)
    @NotNull Button createButton(@NotNull Session session);

    /**
     * Creates a node which allows selecting the element.
     *
     * @param session the session
     * @return a node
     */
    @Contract(pure = true)
    default @NotNull Node createNode(@NotNull Session session) {
        return session.createElementNode(this, createButton(session));
    }

    /**
     * Creates a layer which allows editing the element.
     *
     * @param session the session
     * @return a layer
     */
    @Contract(pure = true)
    @NotNull Layer createLayer(@NotNull Session session);
}
