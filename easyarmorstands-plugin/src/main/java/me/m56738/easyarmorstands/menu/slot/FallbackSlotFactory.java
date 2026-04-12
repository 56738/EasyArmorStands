package me.m56738.easyarmorstands.menu.slot;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FallbackSlotFactory implements MenuSlotFactory {
    @Override
    public @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context) {
        return null;
    }
}
