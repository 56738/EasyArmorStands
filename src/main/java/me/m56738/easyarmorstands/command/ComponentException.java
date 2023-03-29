package me.m56738.easyarmorstands.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class ComponentException extends RuntimeException {
    private final Component component;

    public ComponentException(Component component) {
        super(PlainTextComponentSerializer.plainText().serialize(component));
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }
}
