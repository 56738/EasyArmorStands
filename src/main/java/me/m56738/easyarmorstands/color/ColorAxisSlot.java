package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.capability.item.ItemCapability;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Locale;

public class ColorAxisSlot implements InventorySlot, ColorListener {
    private final ColorPicker menu;
    private final ColorAxis axis;
    private final String name;
    private int slot = -1;
    private Color currentColor;

    public ColorAxisSlot(ColorPicker menu, ColorAxis axis) {
        this.menu = menu;
        this.axis = axis;
        this.name = Util.capitalize(axis.name().toLowerCase(Locale.ROOT));
    }

    protected String getName() {
        return name;
    }

    protected Component getDisplayName() {
        return Component.text(getName(), axis.getTextColor());
    }

    protected DyeColor getDisplayColor() {
        return axis.getDyeColor();
    }

    @Override
    public void initialize(int slot) {
        this.slot = slot;
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        ItemCapability itemCapability = plugin.getCapability(ItemCapability.class);
        ComponentCapability componentCapability = plugin.getCapability(ComponentCapability.class);
        ItemStack item = itemCapability.createColor(getDisplayColor());
        ItemMeta meta = item.getItemMeta();
        componentCapability.setDisplayName(meta, getDisplayName());
        if (currentColor != null) {
            componentCapability.setLore(meta, Collections.singletonList(
                    Component.text(axis.get(currentColor), axis.getTextColor())));
        }
        item.setItemMeta(meta);
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take) {
        if (click) {
            menu.setColor(axis.getColor());
        }
        return false;
    }

    @Override
    public void onColorChanged(Color color) {
        currentColor = color;
        initialize(slot);
    }
}
