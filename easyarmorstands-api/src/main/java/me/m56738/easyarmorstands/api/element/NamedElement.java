package me.m56738.easyarmorstands.api.element;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An element which has a name.
 */
public interface NamedElement extends Element {
    /**
     * Returns the name of the element.
     *
     * @return the name
     */
    @Contract(pure = true)
    @NotNull Component getName();
}
