package me.m56738.easyarmorstands.platform.modded.profile;

import com.mojang.authlib.GameProfile;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import me.m56738.easyarmorstands.platform.profile.Profile;
import net.kyori.adventure.platform.modcommon.MinecraftAudiences;
import net.kyori.adventure.text.object.ObjectContents;
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
import net.minecraft.core.ClientAsset;
import net.minecraft.util.Util;
import net.minecraft.world.item.component.ResolvableProfile;

public interface ModdedProfile extends Profile, ModdedPlatformHolder {
    ResolvableProfile getNative();

    static ModdedProfile fromNative(ModdedPlatform platform, ResolvableProfile profile) {
        return new ModdedProfileImpl(platform, profile);
    }

    static ResolvableProfile toNative(Profile profile) {
        return ((ModdedProfile) profile).getNative();
    }

    @Override
    default PlayerHeadObjectContents asObjectContents() {
        PlayerHeadObjectContents.Builder builder = ObjectContents.playerHead();
        ResolvableProfile profile = getNative();
        GameProfile partialProfile = profile.partialProfile();
        if (profile instanceof ResolvableProfile.Dynamic) {
            if (partialProfile.id().equals(Util.NIL_UUID)) {
                builder.name(partialProfile.name());
            } else {
                builder.id(partialProfile.id());
            }
            return builder.build();
        }
        builder.id(partialProfile.id().equals(Util.NIL_UUID) ? null : partialProfile.id());
        builder.name(partialProfile.name().isEmpty() ? null : partialProfile.name());
        builder.profileProperties(partialProfile.properties().values().stream()
                .map(property -> PlayerHeadObjectContents.property(property.name(), property.value(), property.signature()))
                .toList());
        builder.texture(profile.skinPatch().body().map(ClientAsset.ResourceTexture::id).map(MinecraftAudiences::asAdventure).orElse(null));
        return builder.build();
    }
}
