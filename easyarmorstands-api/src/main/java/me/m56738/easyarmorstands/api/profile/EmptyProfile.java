package me.m56738.easyarmorstands.api.profile;

import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.object.ObjectContents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

class EmptyProfile implements Profile {
    public static final EmptyProfile INSTANCE = new EmptyProfile();

    @Override
    public @Nullable String getName() {
        return null;
    }

    @Override
    public @Nullable UUID getUniqueId() {
        return null;
    }

    @Override
    public @NotNull Component asComponent() {
        return Component.object(ObjectContents.playerHead().build());
    }
}
