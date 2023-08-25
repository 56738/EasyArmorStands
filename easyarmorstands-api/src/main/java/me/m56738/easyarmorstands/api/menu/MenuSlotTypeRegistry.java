package me.m56738.easyarmorstands.api.menu;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

public interface MenuSlotTypeRegistry {
    static MenuSlotTypeRegistry menuSlotTypeRegistry() {
        return Holder.instance;
    }

    void register(MenuSlotType type);

    @Nullable MenuSlotType getOrNull(Key key);

    @ApiStatus.Internal
    class Holder {
        public static MenuSlotTypeRegistry instance;
    }
}
