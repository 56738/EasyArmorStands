package me.m56738.easyarmorstands.paper.property.mannequin;

import me.m56738.easyarmorstands.api.platform.profile.Profile;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.profile.PaperProfile;
import org.bukkit.entity.Mannequin;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class MannequinProfileProperty implements Property<Profile> {
    private final PaperPlatform platform;
    private final Mannequin entity;

    public MannequinProfileProperty(PaperPlatform platform, Mannequin entity) {
        this.platform = platform;
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Profile> getType() {
        return MannequinPropertyTypes.PROFILE;
    }

    @Override
    public @NotNull Profile getValue() {
        return platform.getProfile(entity.getProfile());
    }

    @Override
    public boolean setValue(@NotNull Profile value) {
        entity.setProfile(PaperProfile.toNative(value));
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
