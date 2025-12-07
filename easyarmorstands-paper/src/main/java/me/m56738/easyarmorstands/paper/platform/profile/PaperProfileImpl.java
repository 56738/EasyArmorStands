package me.m56738.easyarmorstands.paper.platform.profile;

import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.profile.PaperProfile;

public record PaperProfileImpl(PaperPlatform platform, ResolvableProfile nativeProfile) implements PaperProfile {
    @Override
    public ResolvableProfile getNative() {
        return nativeProfile;
    }
}
