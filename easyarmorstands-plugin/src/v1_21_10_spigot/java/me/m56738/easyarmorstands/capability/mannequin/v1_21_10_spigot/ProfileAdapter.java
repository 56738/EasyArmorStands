package me.m56738.easyarmorstands.capability.mannequin.v1_21_10_spigot;

import me.m56738.easyarmorstands.api.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.Nullable;

public final class ProfileAdapter {
    private ProfileAdapter() {
    }

    public static Profile fromPlayerProfile(@Nullable PlayerProfile profile) {
        if (profile != null) {
            return new PlayerProfileWrapper(profile);
        } else {
            return Profile.empty();
        }
    }

    public static @Nullable PlayerProfile toPlayerProfile(Profile profile) {
        if (profile instanceof PlayerProfileWrapper) {
            return ((PlayerProfileWrapper) profile).getProfile();
        } else if (profile == Profile.empty()) {
            return null;
        } else {
            return Bukkit.createPlayerProfile(profile.getUniqueId(), profile.getName());
        }
    }
}
