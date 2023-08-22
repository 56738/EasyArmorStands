package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.capability.item.ItemCapability;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Locale;

public class ColorPresetSlot implements MenuSlot {
    private final ColorPickerContext context;
    private final DyeColor color;
    private final Component name;

    public ColorPresetSlot(ColorPickerContext context, DyeColor color) {
        String colorName = color.name().toLowerCase(Locale.ROOT).replace('_', '-');
        this.context = context;
        this.color = color;
        this.name = Message.component("easyarmorstands.color.preset." + colorName)
                .color(TextColor.color(color.getColor().asRGB()));
    }

    @Override
    public ItemStack getItem(Locale locale) {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        ItemCapability itemCapability = plugin.getCapability(ItemCapability.class);
        ComponentCapability componentCapability = plugin.getCapability(ComponentCapability.class);
        ItemStack item = itemCapability.createColor(color);
        ItemMeta meta = item.getItemMeta();
        componentCapability.setDisplayName(meta, name);
        componentCapability.setLore(meta, Arrays.asList(
                GlobalTranslator.render(Message.hint("easyarmorstands.menu.color-picker.left-click-to-select"), locale),
                GlobalTranslator.render(Message.hint("easyarmorstands.menu.color-picker.right-click-to-mix"), locale)));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void onClick(MenuClick click) {
        if (click.isLeftClick()) {
            context.setColor(color.getColor(), click.menu());
        } else if (click.isRightClick()) {
            Color currentColor = context.getColor();
            if (currentColor != null) {
                context.setColor(currentColor.mixDyes(color), click.menu());
            }
        }
    }
}
