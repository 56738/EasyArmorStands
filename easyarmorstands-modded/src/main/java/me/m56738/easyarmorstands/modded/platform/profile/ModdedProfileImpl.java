package me.m56738.easyarmorstands.modded.platform.profile;

import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.api.platform.profile.ModdedProfile;
import net.minecraft.world.item.component.ResolvableProfile;

public record ModdedProfileImpl(ModdedPlatform platform, ResolvableProfile nativeProfile) implements ModdedProfile {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public ResolvableProfile getNative() {
        return nativeProfile;
    }
}
