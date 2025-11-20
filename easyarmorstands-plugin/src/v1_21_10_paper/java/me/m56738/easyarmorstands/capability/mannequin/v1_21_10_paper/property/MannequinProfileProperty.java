package me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.property;

import me.m56738.easyarmorstands.api.profile.Profile;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.capability.mannequin.v1_21_10_paper.ProfileAdapter;
import org.bukkit.entity.Mannequin;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class MannequinProfileProperty implements Property<Profile> {
    private final Mannequin entity;

    public MannequinProfileProperty(Mannequin entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Profile> getType() {
        return MannequinPropertyTypes.PROFILE;
    }

    @Override
    public @NotNull Profile getValue() {
        return ProfileAdapter.fromResolvableProfile(entity.getProfile());
    }

    @Override
    public boolean setValue(@NotNull Profile value) {
        entity.setProfile(ProfileAdapter.toResolvableProfile(value));
        return true;
    }
}
