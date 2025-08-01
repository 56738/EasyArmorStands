package me.m56738.easyarmorstands.neoforge.api.event.element;

import me.m56738.easyarmorstands.api.element.DefaultEntityElement;
import net.neoforged.bus.api.Event;

public class DefaultEntityElementInitializeEvent extends Event {
    private final DefaultEntityElement element;

    public DefaultEntityElementInitializeEvent(DefaultEntityElement element) {
        this.element = element;
    }

    public DefaultEntityElement getElement() {
        return element;
    }
}
