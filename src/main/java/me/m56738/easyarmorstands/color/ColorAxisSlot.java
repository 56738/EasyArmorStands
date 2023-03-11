package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.capability.item.ItemCapability;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;
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

    protected Component getDisplayName() {
        String result = name;
        if (currentColor != null) {
            result += ": " + axis.get(currentColor);
        }
        return Component.text(result, axis.getTextColor());
    }

    protected DyeColor getDisplayColor() {
        return axis.getDyeColor();
    }

    protected List<Component> getDescription() {
        if (currentColor != null) {
            String red = String.format("%02X", currentColor.getRed());
            String green = String.format("%02X", currentColor.getGreen());
            String blue = String.format("%02X", currentColor.getBlue());
            return Collections.singletonList(
                    Component.text()
                            .content("#")
                            .append(Component.text(red, NamedTextColor.RED))
                            .append(Component.text(green, NamedTextColor.GREEN))
                            .append(Component.text(blue, NamedTextColor.BLUE))
                            .color(NamedTextColor.GRAY)
                            .build()
            );
        }
        return Collections.emptyList();
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
        componentCapability.setLore(meta, getDescription());
        item.setItemMeta(meta);
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ClickType type) {
        return false;
    }

    @Override
    public void onColorChanged(Color color) {
        currentColor = color;
        initialize(slot);
    }
}
