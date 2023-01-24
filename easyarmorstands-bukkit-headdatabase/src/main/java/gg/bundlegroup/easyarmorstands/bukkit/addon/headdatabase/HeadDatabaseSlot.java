package gg.bundlegroup.easyarmorstands.bukkit.addon.headdatabase;

import gg.bundlegroup.easyarmorstands.bukkit.platform.BukkitPlatform;
import gg.bundlegroup.easyarmorstands.bukkit.platform.BukkitPlayer;
import gg.bundlegroup.easyarmorstands.core.inventory.InventorySlot;
import gg.bundlegroup.easyarmorstands.core.inventory.SessionMenu;
import gg.bundlegroup.easyarmorstands.core.platform.EasItem;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class HeadDatabaseSlot implements InventorySlot {
    private final BukkitPlatform platform;
    private final SessionMenu menu;
    private final HeadDatabaseAPI api;
    private final Player player;

    public HeadDatabaseSlot(BukkitPlatform platform, SessionMenu menu, HeadDatabaseAPI api) {
        this.platform = platform;
        this.menu = menu;
        this.api = api;
        this.player = ((BukkitPlayer) menu.getSession().getPlayer()).get();
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
        menu.getInventory().setItem(slot, platform.getItem(item));
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, EasItem cursor) {
        if (put) {
            // Delete items placed into this slot
            menu.queueTask(() -> menu.getSession().getPlayer().setCursor(null));
            return false;
        }
        player.performCommand("headdb");
        return false;
    }
}
