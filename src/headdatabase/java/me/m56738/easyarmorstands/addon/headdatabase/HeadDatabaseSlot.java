package me.m56738.easyarmorstands.addon.headdatabase;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.menu.SessionMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class HeadDatabaseSlot implements InventorySlot {
    private final SessionMenu menu;
    private final HeadDatabaseAPI api;
    private final Player player;

    public HeadDatabaseSlot(SessionMenu menu, HeadDatabaseAPI api) {
        this.menu = menu;
        this.api = api;
        this.player = menu.getSession().getPlayer();
    }

    @Override
    public void initialize(int slot) {
        ItemStack item = api.getItemHead("227");
        if (item == null) {
            item = api.getRandomHead();
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "Open head database");
        meta.setLore(Arrays.asList(
                ChatColor.GRAY + "Select a custom head",
                ChatColor.GRAY + "using Head Database."
        ));
        item.setItemMeta(meta);
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take) {
        if (put) {
            // Delete items placed into this slot
            menu.queueTask(() -> menu.getSession().getPlayer().setItemOnCursor(null));
            return false;
        }
        menu.queueTask(() -> player.performCommand("headdb"));
        return false;
    }
}
