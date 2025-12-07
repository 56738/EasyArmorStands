package me.m56738.easyarmorstands.paper.api.platform.profile;

import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.api.platform.profile.Profile;

public interface PaperProfile extends Profile {
    static ResolvableProfile toNative(Profile profile) {
        return ((PaperProfile) profile).getNative();
    }

    ResolvableProfile getNative();
}
