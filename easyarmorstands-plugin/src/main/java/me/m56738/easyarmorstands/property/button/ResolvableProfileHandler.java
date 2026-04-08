package me.m56738.easyarmorstands.property.button;

import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.api.property.Property;

@SuppressWarnings("UnstableApiUsage")
public class ResolvableProfileHandler implements ButtonHandler {
    private final Property<ResolvableProfile> property;

    public ResolvableProfileHandler(Property<ResolvableProfile> property) {
        this.property = property;
    }

    @Override
    public void onClick(MenuClickContext context) {
        // TODO
    }
}
