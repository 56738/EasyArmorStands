package me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper;

import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.api.profile.Profile;

@SuppressWarnings("UnstableApiUsage")
public final class ProfileAdapter {
    private ProfileAdapter() {
    }

    public static Profile fromResolvableProfile(ResolvableProfile profile) {
        if (profile.name() == null && profile.uuid() == null && profile.properties().isEmpty()) {
            return Profile.empty();
        }
        return new ResolvableProfileWrapper(profile);
    }

    @SuppressWarnings("PatternValidation")
    public static ResolvableProfile toResolvableProfile(Profile profile) {
        if (profile instanceof ResolvableProfileWrapper) {
            return ((ResolvableProfileWrapper) profile).getProfile();
        }
        return ResolvableProfile.resolvableProfile()
                .name(profile.getName())
                .uuid(profile.getUniqueId())
                .build();
    }
}
