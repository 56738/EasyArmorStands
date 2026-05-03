package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.property.PropertyContainer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Any object with properties.
 * <p>
 * Usually an {@link EntityElement entity}, but it could also be something else.
 */
public interface Element {
    /**
     * The type of this element.
     *
     * @return the type
     */
    @Contract(pure = true)
    @NotNull ElementType getType();

    /**
     * The properties of the element.
     * <p>
     * Changes to these properties will be performed immediately without any permission checks or history tracking.
     *
     * @return the properties of the element
     * @see me.m56738.easyarmorstands.api.editor.Session#properties(Element) Permission checks and history tracking
     */
    @Contract(pure = true)
    @NotNull PropertyContainer getProperties();

    /**
     * A reference which can be used to retrieve the element later.
     * <p>
     * The reference should remain valid even after the chunk is unloaded and loaded or the element is deleted
     * and recreated.
     * It is stored in the history and used to find the element when undoing or redoing a change.
     *
     * @return a reference which can be used to retrieve the element
     */
    @Contract(pure = true)
    @NotNull ElementReference getReference();

    @Contract(pure = true)
    boolean isValid();
}
