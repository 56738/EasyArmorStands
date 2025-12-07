package me.m56738.easyarmorstands.paper.property.mannequin;

import me.m56738.easyarmorstands.api.platform.profile.Profile;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import me.m56738.easyarmorstands.paper.api.platform.profile.PaperProfile;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.Mannequin;

@SuppressWarnings("UnstableApiUsage")
public class MannequinProfileProperty extends EntityProperty<Mannequin, Profile> {
    private final PaperPlatform platform;

    public MannequinProfileProperty(PaperPlatform platform, Mannequin entity) {
        super(entity);
        this.platform = platform;
    }

    @Override
    public PropertyType<Profile> getType() {
        return MannequinPropertyTypes.PROFILE;
    }

    @Override
    public Profile getValue() {
        return platform.getProfile(entity.getProfile());
    }

    @Override
    public boolean setValue(Profile value) {
        entity.setProfile(PaperProfile.toNative(value));
        return true;
    }
}
