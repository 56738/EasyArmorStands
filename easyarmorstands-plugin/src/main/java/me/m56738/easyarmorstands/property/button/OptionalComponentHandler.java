package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import net.kyori.adventure.text.Component;

import java.util.Optional;

public class OptionalComponentHandler implements ButtonHandler {
    private final Property<Optional<Component>> property;

    public OptionalComponentHandler(Property<Optional<Component>> property) {
        this.property = property;
    }

    @Override
    public void onClick(MenuClickContext context) {
        // TODO
    }
}
