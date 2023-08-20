package me.m56738.easyarmorstands.addon.headdatabase;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Locale;

public class HeadDatabaseSlot implements MenuSlot {
    private final HeadDatabaseAPI api;

    public HeadDatabaseSlot(HeadDatabaseAPI api) {
        this.api = api;
    }

    @Override
    public ItemStack getItem(Locale locale) {
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
        return item;
    }

    @Override
    public void onClick(MenuClick click) {
        if (!click.isLeftClick()) {
            return;
        }
        click.queueTask(() -> {
            Player player = click.player().get();
            player.setItemOnCursor(null);
            player.performCommand("headdb");
        });
        click.close();
    }
}
