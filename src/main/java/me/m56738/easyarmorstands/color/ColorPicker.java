package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.capability.itemcolor.ItemColorCapability;
import me.m56738.easyarmorstands.inventory.DisabledSlot;
import me.m56738.easyarmorstands.inventory.InventoryMenu;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ColorPicker extends InventoryMenu {
    private final Player player;
    private Color color;

    public ColorPicker(ItemStack item, Player player) {
        super(4, "EasyArmorStands color picker");
        this.player = player;
        initialize();
        if (item != null) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                ItemColorCapability itemColorCapability = EasyArmorStands.getInstance().getCapability(ItemColorCapability.class);
                color = itemColorCapability.getColor(meta);
            }
        }
        if (color == null) {
            color = Color.WHITE;
        }
        getInventory().setItem(2, item);
        setColor(color);
    }

    @SuppressWarnings("deprecation")
    private void initialize() {
        for (int i = 0; i < 5; i++) {
            setSlot(0, i, new ColorItemSlot(this));
        }

        ColorAxis[] axes = ColorAxis.values();
        for (int i = 0; i < axes.length; i++) {
            ColorAxis axis = axes[i];
            // names unstable across versions, use legacy numbers
            DyeColor black = DyeColor.getByWoolData((byte) 15);
            DyeColor gray = DyeColor.getByWoolData((byte) 7);
            DyeColor lightGray = DyeColor.getByWoolData((byte) 8);
            DyeColor white = DyeColor.getByWoolData((byte) 0);
            setSlot(i + 1, 0, new ColorAxisChangeSlot(this, axis, black, -10));
            setSlot(i + 1, 1, new ColorAxisChangeSlot(this, axis, gray, -1));
            setSlot(i + 1, 2, new ColorAxisSlot(this, axis));
            setSlot(i + 1, 3, new ColorAxisChangeSlot(this, axis, lightGray, 1));
            setSlot(i + 1, 4, new ColorAxisChangeSlot(this, axis, white, 10));
        }

        int row = 0;
        int column = 5;
        for (DyeColor color : DyeColor.values()) {
            setSlot(row, column, new ColorPresetSlot(this, color));
            column++;
            if (column >= 9) {
                row++;
                column = 5;
                if (row >= 4) {
                    break;
                }
            }
        }

        setEmptySlots(new DisabledSlot(this, Util.createItem(ItemType.LIGHT_BLUE_STAINED_GLASS_PANE,
                Component.empty(), Collections.emptyList())));
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        for (InventorySlot slot : slots) {
            if (slot instanceof ColorListener) {
                ((ColorListener) slot).onColorChanged(color);
            }
        }
    }

    @Override
    public void onClose() {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] instanceof ColorItemSlot) {
                ItemStack item = getInventory().getItem(i);
                if (item != null) {
                    items.add(item);
                    getInventory().setItem(i, null);
                }
            }
        }
        HashMap<Integer, ItemStack> remaining = player.getInventory().addItem(items.toArray(new ItemStack[0]));
        for (ItemStack item : remaining.values()) {
            player.getWorld().dropItem(player.getLocation(), item);
        }
    }

    public boolean onTake(int slot) {
        return true;
    }
}
