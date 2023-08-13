package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BackgroundSlot implements MenuSlot {
    public static final BackgroundSlot INSTANCE = new BackgroundSlot();

    private final ItemStack item = Util.createItem(ItemType.LIGHT_BLUE_STAINED_GLASS_PANE, Component.empty());

    private BackgroundSlot() {
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public void onClick(MenuClick click) {
        if (click.isRightClick() && click.cursor().getType() == Material.AIR) {
            click.close();
        }
    }
}
