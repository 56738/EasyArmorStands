package me.m56738.easyarmorstands.paper.api.platform.profile;

import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.api.platform.profile.Profile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.object.ObjectContents;

import java.util.Objects;

@SuppressWarnings("UnstableApiUsage")
public interface PaperProfile extends Profile {
    static ResolvableProfile toNative(Profile profile) {
        return ((PaperProfile) profile).getNative();
    }

    ResolvableProfile getNative();

    @Override
    default Component asComponent() {
        return Component.object(ObjectContents.playerHead(getNative()));
    }

    @Override
    default String asString() {
        return Objects.requireNonNullElse(getNative().name(), "Unknown");
    }
}
