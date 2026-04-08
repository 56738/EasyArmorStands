package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import net.kyori.adventure.text.Component;

public class ComponentHandler implements ButtonHandler {
    private final Property<Component> property;

    public ComponentHandler(Property<Component> property) {
        this.property = property;
    }

    @Override
    public void onClick(MenuClickContext context) {
        // TODO
    }
}
