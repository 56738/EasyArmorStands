package me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper;

import io.papermc.paper.datacomponent.item.ResolvableProfile;
import me.m56738.easyarmorstands.api.profile.Profile;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.object.ObjectContents;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.object.PlayerHeadObjectContents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class ResolvableProfileWrapper implements Profile {
    private final ResolvableProfile profile;

    public ResolvableProfileWrapper(ResolvableProfile profile) {
        this.profile = profile;
    }

    public ResolvableProfile getProfile() {
        return profile;
    }

    @Override
    public @Nullable String getName() {
        return profile.name();
    }

    @Override
    public @Nullable UUID getUniqueId() {
        return profile.uuid();
    }

    @Override
    public @NotNull Component asComponent() {
        PlayerHeadObjectContents.Builder builder = ObjectContents.playerHead();
        builder.name(profile.name());
        builder.id(profile.uuid());
        profile.properties().forEach(property -> builder.profileProperty(PlayerHeadObjectContents.property(property.getName(), property.getValue(), property.getSignature())));
        return Component.object(builder.build());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ResolvableProfileWrapper that = (ResolvableProfileWrapper) o;
        return Objects.equals(profile, that.profile);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(profile);
    }
}
