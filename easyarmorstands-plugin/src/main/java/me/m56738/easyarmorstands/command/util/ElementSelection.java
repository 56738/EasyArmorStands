package me.m56738.easyarmorstands.command.util;

import me.m56738.easyarmorstands.api.element.Element;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;
import java.util.Collections;

@NullMarked
public record ElementSelection(Collection<Element> elements) {
    public ElementSelection(Collection<Element> elements) {
        this.elements = Collections.unmodifiableCollection(elements);
    }
}
