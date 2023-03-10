package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.capability.itemcolor.ItemColorCapability;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ColorPickerSlot implements InventorySlot {
    private final SessionMenu menu;

    public ColorPickerSlot(SessionMenu menu) {
        this.menu = menu;
    }

    @Override
    public void initialize(int slot) {
        ItemStack item = Util.createItem(
                ItemType.LEATHER_CHESTPLATE,
                Component.text("Open color picker", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Place an item in this", NamedTextColor.GRAY),
                        Component.text("slot to edit its color.", NamedTextColor.GRAY)
                )
        );
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ItemStack cursor) {
        if (!put) {
            return false;
        }
        if (cursor == null) {
            return false;
        }
        ItemStack item = cursor.clone();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        if (!EasyArmorStands.getInstance().getCapability(ItemColorCapability.class).hasColor(meta)) {
            return false;
        }
        cursor.setAmount(0);
        Player player = menu.getSession().getPlayer();
        player.openInventory(new SessionColorPicker(item, player, menu).getInventory());
        return false;
    }
}
