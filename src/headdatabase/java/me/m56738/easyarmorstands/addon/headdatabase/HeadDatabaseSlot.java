package me.m56738.easyarmorstands.addon.headdatabase;

import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class HeadDatabaseSlot implements MenuSlot {
    private final HeadDatabaseAddon addon;

    public HeadDatabaseSlot(HeadDatabaseAddon addon) {
        this.addon = addon;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return addon.getButtonTemplate().render(locale);
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
    }
}
