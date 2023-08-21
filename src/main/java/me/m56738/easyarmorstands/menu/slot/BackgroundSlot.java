package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.menu.MenuClick;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class BackgroundSlot implements MenuSlot {
    public static final BackgroundSlot INSTANCE = new BackgroundSlot();

    private BackgroundSlot() {
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return EasyArmorStands.getInstance().getConfiguration().getBackgroundTemplate().render(locale);
    }

    @Override
    public void onClick(MenuClick click) {
        if (click.isRightClick() && click.cursor().getType() == Material.AIR) {
            click.close();
        }
    }
}
