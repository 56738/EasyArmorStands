package me.m56738.easyarmorstands.api.property.renderer;

import me.m56738.easyarmorstands.api.platform.profile.Profile;
import net.kyori.adventure.text.Component;

class ProfileValueRenderer implements ValueRenderer<Profile> {
    @Override
    public Component renderComponent(Profile value) {
        return value.asComponent();
    }

    @Override
    public String renderString(Profile value) {
        return value.asString();
    }
}
