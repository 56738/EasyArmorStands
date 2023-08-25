package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.MenuSlotContext;
import me.m56738.easyarmorstands.api.menu.MenuSlotFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FallbackSlotFactory implements MenuSlotFactory {
    @Override
    public @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context) {
        return null;
    }
}
