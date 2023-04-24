package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.color.ColorPicker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SessionColorPicker extends ColorPicker {
    private final Player player;
    private final ArmorStandMenu menu;

    public SessionColorPicker(ItemStack item, Player player, ArmorStandMenu menu) {
        super(item, player);
        this.player = player;
        this.menu = menu;
    }

    @Override
    public boolean onTake(int slot) {
        queueTask(() -> {
            ItemStack item = getInventory().getItem(slot);
            getInventory().setItem(slot, null);
            player.openInventory(menu.getInventory());
            player.setItemOnCursor(item);
        });
        return false;
    }
}
