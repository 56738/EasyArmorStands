package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.capability.item.ItemCapability;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ColorAxisSlot implements ColorSlot {
    private final ColorPickerContext context;
    private final ColorAxis axis;
    private final Component name;

    public ColorAxisSlot(ColorPickerContext context, ColorAxis axis) {
        this.context = context;
        this.axis = axis;
        this.name = axis.getDisplayName();
    }

    protected DyeColor getItemColor() {
        return axis.getDyeColor();
    }

    protected Component getDisplayName() {
        Color color = context.getColor();
        Component result = name;
        if (color != null) {
            result = result.append(Component.text(": " + axis.get(color)));
        }
        return result;
    }

    protected List<Component> getDescription(Locale locale) {
        Color color = context.getColor();
        if (color != null) {
            return Collections.singletonList(Util.formatColor(color));
        }
        return Collections.emptyList();
    }

    @Override
    public ItemStack getItem(Locale locale) {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        ItemCapability itemCapability = plugin.getCapability(ItemCapability.class);
        ComponentCapability componentCapability = plugin.getCapability(ComponentCapability.class);
        ItemStack item = itemCapability.createColor(getItemColor());
        ItemMeta meta = item.getItemMeta();
        componentCapability.setDisplayName(meta, getDisplayName());
        componentCapability.setLore(meta, getDescription(locale));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void onClick(MenuClick click) {
    }
}
