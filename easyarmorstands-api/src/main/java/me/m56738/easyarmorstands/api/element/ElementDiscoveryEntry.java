package me.m56738.easyarmorstands.api.element;

import org.jetbrains.annotations.Nullable;

/**
 * A discovered selectable element.
 * <p>
 * <b>Must implement {@link Object#equals equals} and {@link Object#hashCode hashCode}.</b>
 * <p>
 * This is used to deduplicate discovered elements.
 */
public interface ElementDiscoveryEntry {
    @Nullable SelectableElement getElement();
}
