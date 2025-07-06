package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.menu.slot.MenuSlotType;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class MenuSlotTypeRegistryImpl implements MenuSlotTypeRegistry {
    private final Map<Key, MenuSlotType> types = new HashMap<>();

    @Override
    public void register(@NotNull MenuSlotType type) {
        types.put(type.key(), type);
    }

    @Override
    public @Nullable MenuSlotType getOrNull(@NotNull Key key) {
        return types.get(key);
    }
}
