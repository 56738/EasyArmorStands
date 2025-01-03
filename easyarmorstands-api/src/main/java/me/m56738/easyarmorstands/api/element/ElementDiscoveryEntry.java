package me.m56738.easyarmorstands.api.element;

import org.jetbrains.annotations.Nullable;

/**
 * A discovered selectable element.
 * <p>
 * <b>Must implement {@link Object#equals equals} and {@link Object#hashCode hashCode}.</b>
 * Discovering and comparing entries should be as fast as possible.
 * <p>
 * This is used to deduplicate discovered elements.
 */
public interface ElementDiscoveryEntry {
    /**
     * Attempts to create an element instance representing this entry.
     * <p>
     * May return {@code null}.
     *
     * @return the discovered element or {@code null}
     */
    @Nullable SelectableElement getElement();
}
