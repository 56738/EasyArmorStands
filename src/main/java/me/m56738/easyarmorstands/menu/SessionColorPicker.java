package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.color.ColorPicker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SessionColorPicker extends ColorPicker {
    private final Player player;
    private final SessionMenu menu;

    public SessionColorPicker(ItemStack item, Player player, SessionMenu menu) {
        super(item, player);
        this.player = player;
        this.menu = menu;
    }

    @Override
    public boolean onTake(int slot) {
        ItemStack item = getInventory().getItem(slot);
        if (item != null) {
            finish(item);
            getInventory().setItem(slot, null);
        }
        return false;
    }

    private void finish(ItemStack item) {
        Bukkit.getScheduler().runTask(EasyArmorStands.getInstance(), () -> {
            player.openInventory(menu.getInventory());
            player.setItemOnCursor(item);
        });
    }
}
