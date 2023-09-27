package me.m56738.easyarmorstands.api.menu;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public interface MenuSlot {
    @Nullable ItemStack getItem(Locale locale);

    void onClick(@NotNull MenuClick click);
}
