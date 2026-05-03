package me.m56738.easyarmorstands.command.util;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.group.property.GroupPropertyContainer;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ElementSelection {
    private final Collection<Element> elements;

    public ElementSelection(Collection<Element> elements) {
        this.elements = Collections.unmodifiableCollection(elements);
    }

    public @NotNull Collection<Element> elements() {
        return elements;
    }

    public @NotNull PropertyContainer properties(ChangeContext context) {
        if (elements.size() == 1) {
            return new TrackedPropertyContainer(elements.iterator().next(), context);
        }
        List<PropertyContainer> containers = new ArrayList<>(elements.size());
        for (Element element : elements) {
            containers.add(new TrackedPropertyContainer(element, context));
        }
        return new GroupPropertyContainer(containers);
    }
}
