package me.m56738.easyarmorstands.api.menu;

import me.m56738.easyarmorstands.api.platform.inventory.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public interface MenuSlot {
    @Nullable Item getItem(Locale locale);

    void onClick(@NotNull MenuClick click);
}
