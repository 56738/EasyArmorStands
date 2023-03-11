package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.capability.item.ItemCapability;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Locale;

public class ColorPresetSlot implements InventorySlot {
    private final ColorPicker menu;
    private final DyeColor color;
    private final String name;

    public ColorPresetSlot(ColorPicker menu, DyeColor color) {
        this.menu = menu;
        this.color = color;
        String name = color.name().replace('_', ' ').toLowerCase(Locale.ROOT);
        this.name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    @Override
    public void initialize(int slot) {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        ItemCapability itemCapability = plugin.getCapability(ItemCapability.class);
        ComponentCapability componentCapability = plugin.getCapability(ComponentCapability.class);
        ItemStack item = itemCapability.createColor(color);
        ItemMeta meta = item.getItemMeta();
        componentCapability.setDisplayName(meta, Component.text(name, TextColor.color(color.getColor().asRGB())));
        item.setItemMeta(meta);
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take) {
        if (click) {
            menu.setColor(color.getColor());
        }
        return false;
    }
}
