package me.m56738.easyarmorstands.api.profile;

import me.m56738.easyarmorstands.lib.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface Profile extends ComponentLike {
    static Profile empty() {
        return EmptyProfile.INSTANCE;
    }

    @Nullable
    String getName();

    @Nullable
    UUID getUniqueId();
}
