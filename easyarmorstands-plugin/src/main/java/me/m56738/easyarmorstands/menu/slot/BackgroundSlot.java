package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class BackgroundSlot implements MenuSlot {
    private final ItemStack item;

    public BackgroundSlot(ItemStack item) {
        this.item = item;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return item;
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (click.isRightClick() && click.cursor().getType() == Material.AIR) {
            click.close();
        }
    }
}
