package me.m56738.easyarmorstands.platform.modded.profile;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.item.component.ResolvableProfile;

record ModdedProfileImpl(ModdedPlatform platform, ResolvableProfile profile) implements ModdedProfile {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public ResolvableProfile getNative() {
        return profile;
    }
}
