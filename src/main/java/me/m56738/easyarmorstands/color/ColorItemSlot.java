package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.itemcolor.ItemColorCapability;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import org.bukkit.Color;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ColorItemSlot implements InventorySlot, ColorListener {
    private final ColorPicker menu;
    private int slot = -1;
    private Color color;

    public ColorItemSlot(ColorPicker menu) {
        this.menu = menu;
    }

    @Override
    public void initialize(int slot) {
        this.slot = slot;
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ClickType type) {
        if (take) {
            return menu.onTake(slot);
        }
        if (put) {
            menu.queueTask(() -> onColorChanged(color));
        }
        return true;
    }

    @Override
    public void onColorChanged(Color color) {
        this.color = color;
        ItemStack item = menu.getInventory().getItem(slot);
        if (item == null) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }
        ItemColorCapability itemColorCapabilityProvider = EasyArmorStands.getInstance().getCapability(ItemColorCapability.class);
        if (itemColorCapabilityProvider.setColor(meta, color)) {
            item.setItemMeta(meta);
            menu.getInventory().setItem(slot, item);
        }
    }
}
