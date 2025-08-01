package me.m56738.easyarmorstands.api.menu;

import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MenuSlotTypeRegistry {
    void register(@NotNull MenuSlotType type);

    @Nullable MenuSlotType getOrNull(@NotNull Key key);
}
