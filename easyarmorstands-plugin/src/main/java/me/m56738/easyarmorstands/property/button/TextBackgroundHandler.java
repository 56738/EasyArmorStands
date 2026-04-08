package me.m56738.easyarmorstands.property.button;

import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;
import org.bukkit.Color;

import java.util.Optional;

public class TextBackgroundHandler implements ButtonHandler {
    private final Property<Optional<Color>> property;

    public TextBackgroundHandler(Property<Optional<Color>> property) {
        this.property = property;
    }

    @Override
    public void onClick(MenuClickContext context) {
        // TODO
    }
}
