package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ColorPickerSlot implements MenuSlot {
    @Override
    public ItemStack getItem() {
        return Util.createItem(
                ItemType.LEATHER_CHESTPLATE,
                Component.text("Open color picker", NamedTextColor.BLUE),
                Arrays.asList(
                        Component.text("Place an item in this", NamedTextColor.GRAY),
                        Component.text("slot to edit its color.", NamedTextColor.GRAY)
                )
        );
    }

    @Override
    public void onClick(MenuClick click) {
        click.cancel();
    }

//    @Override
//    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ClickType type) {
//        if (!put) {
//            return false;
//        }
//        menu.queueTask(() -> {
//            Player player = menu.getSession().getPlayer();
//            ItemStack item = player.getItemOnCursor();
//            if (item == null) {
//                return;
//            }
//            ItemMeta meta = item.getItemMeta();
//            if (meta == null) {
//                return;
//            }
//            if (!EasyArmorStands.getInstance().getCapability(ItemColorCapability.class).hasColor(meta)) {
//                return;
//            }
//            player.setItemOnCursor(null);
//            player.openInventory(new SessionColorPicker(item, player, menu).getInventory());
//        });
//        return false;
//    }
}
