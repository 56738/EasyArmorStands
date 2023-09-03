package me.m56738.easyarmorstands.api.menu;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MenuSlotFactory {
    @Nullable MenuSlot createSlot(@NotNull MenuSlotContext context);
}
