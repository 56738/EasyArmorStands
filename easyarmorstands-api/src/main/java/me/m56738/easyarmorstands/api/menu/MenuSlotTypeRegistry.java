package me.m56738.easyarmorstands.api.menu;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Nullable;

public interface MenuSlotTypeRegistry {
    void register(MenuSlotType type);

    @Nullable MenuSlotType getOrNull(Key key);
}
