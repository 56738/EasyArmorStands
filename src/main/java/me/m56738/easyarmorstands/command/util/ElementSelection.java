package me.m56738.easyarmorstands.command.util;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.group.property.GroupPropertyContainer;
import me.m56738.easyarmorstands.property.TrackedPropertyContainer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ElementSelection {
    private final EasyArmorStandsCommon eas;
    private final Collection<Element> elements;

    public ElementSelection(EasyArmorStandsCommon eas, Collection<Element> elements) {
        this.eas = eas;
        this.elements = Collections.unmodifiableCollection(elements);
    }

    public @NotNull Collection<Element> elements() {
        return elements;
    }

    public @NotNull PropertyContainer properties(EasPlayer player) {
        if (elements.size() == 1) {
            return new TrackedPropertyContainer(eas, elements.iterator().next(), player);
        }
        List<PropertyContainer> containers = new ArrayList<>(elements.size());
        for (Element element : elements) {
            containers.add(new TrackedPropertyContainer(eas, element, player));
        }
        return new GroupPropertyContainer(containers);
    }
}
