package me.m56738.easyarmorstands.modded.api.platform.profile;

import me.m56738.easyarmorstands.api.platform.profile.Profile;
import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatformHolder;
import net.kyori.adventure.text.Component;
import net.minecraft.network.chat.contents.objects.PlayerSprite;
import net.minecraft.world.item.component.ResolvableProfile;

public interface ModdedProfile extends Profile, ModdedPlatformHolder {
    static ResolvableProfile toNative(Profile profile) {
        return ((ModdedProfile) profile).getNative();
    }

    ResolvableProfile getNative();

    @Override
    default Component asComponent() {
        return getPlatform().getAdventure().asAdventure(
                net.minecraft.network.chat.Component.object(new PlayerSprite(getNative(), true)));
    }

    @Override
    default String asString() {
        return getNative().name().orElse("Unknown");
    }
}
