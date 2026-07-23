package me.m56738.easyarmorstands.platform.paper.profile;

import io.papermc.paper.datacomponent.item.ResolvableProfile;

@SuppressWarnings("UnstableApiUsage")
record PaperProfileImpl(ResolvableProfile profile) implements PaperProfile {
    @Override
    public ResolvableProfile getNative() {
        return profile;
    }
}
