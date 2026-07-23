package me.m56738.easyarmorstands.platform.paper.profile;

import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.platform.profile.Profile;
import net.kyori.adventure.text.object.PlayerHeadObjectContents;

@SuppressWarnings("UnstableApiUsage")
public interface PaperProfile extends Profile {
    static PaperProfile fromNative(ResolvableProfile profile) {
        return new PaperProfileImpl(profile);
    }

    ResolvableProfile getNative();

    static ResolvableProfile toNative(Profile profile) {
        return ((PaperProfile) profile).getNative();
    }

    @Override
    default void applySkinToPlayerHeadContents(PlayerHeadObjectContents.Builder builder) {
        getNative().applySkinToPlayerHeadContents(builder);
    }
}
