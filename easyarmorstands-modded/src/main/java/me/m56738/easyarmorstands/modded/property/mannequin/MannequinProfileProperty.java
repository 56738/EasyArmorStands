package me.m56738.easyarmorstands.modded.property.mannequin;

import me.m56738.easyarmorstands.api.platform.profile.Profile;
import me.m56738.easyarmorstands.api.property.type.MannequinPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.api.platform.profile.ModdedProfile;
import me.m56738.easyarmorstands.modded.property.entity.EntityProperty;
import net.minecraft.world.entity.decoration.Mannequin;

public class MannequinProfileProperty extends EntityProperty<Mannequin, Profile> {
    private final ModdedPlatform platform;

    public MannequinProfileProperty(Mannequin entity, ModdedPlatform platform) {
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
        entity.setProfile(ModdedProfile.toNative(value));
        return true;
    }
}
