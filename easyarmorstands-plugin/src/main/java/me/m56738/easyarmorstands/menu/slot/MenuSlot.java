package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.menu.click.MenuClick;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public interface MenuSlot {
    @Nullable ItemStack getItem(Locale locale);

    void onClick(@NotNull MenuClick click);
}
