package me.m56738.easyarmorstands.capability.mannequin.v1_21_10_spigot;

import me.m56738.easyarmorstands.api.profile.Profile;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.object.ObjectContents;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.object.PlayerHeadObjectContents;
import org.bukkit.profile.PlayerProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class PlayerProfileWrapper implements Profile {
    private final PlayerProfile profile;

    public PlayerProfileWrapper(PlayerProfile profile) {
        this.profile = profile;
    }

    public PlayerProfile getProfile() {
        return profile;
    }

    @Override
    public @Nullable String getName() {
        return profile.getName();
    }

    @Override
    public @Nullable UUID getUniqueId() {
        return profile.getUniqueId();
    }

    @Override
    public @NotNull Component asComponent() {
        String value = null;
        try {
            value = TextureAdapter.getValue(profile.getTextures());
        } catch (Throwable ignored) {
        }
        if (value != null) {
            PlayerHeadObjectContents.Builder builder = ObjectContents.playerHead();
            builder.name(profile.getName());
            builder.id(profile.getUniqueId());
            builder.profileProperty(PlayerHeadObjectContents.property("textures", value));
            return Component.object(builder.build());
        }

        String name = getName();
        if (name != null) {
            return Component.text(name);
        }

        UUID id = getUniqueId();
        if (id != null) {
            return Component.text(id.toString());
        }

        return Component.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlayerProfileWrapper that = (PlayerProfileWrapper) o;
        return Objects.equals(profile, that.profile);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(profile);
    }
}
