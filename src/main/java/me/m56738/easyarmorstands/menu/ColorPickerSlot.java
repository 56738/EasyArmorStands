package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.capability.itemcolor.ItemColorCapability;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ColorPickerSlot implements InventorySlot {
    private final EntityMenu<?> menu;

    public ColorPickerSlot(EntityMenu<?> menu) {
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
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ClickType type) {
        if (!put) {
            return false;
        }
        menu.queueTask(() -> {
            Player player = menu.getSession().getPlayer();
            ItemStack item = player.getItemOnCursor();
            if (item == null) {
                return;
            }
            ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                return;
            }
            if (!EasyArmorStands.getInstance().getCapability(ItemColorCapability.class).hasColor(meta)) {
                return;
            }
            player.setItemOnCursor(null);
            player.openInventory(new SessionColorPicker(item, player, menu).getInventory());
        });
        return false;
    }
}
