package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.capability.itemcolor.ItemColorCapability;
import me.m56738.easyarmorstands.color.ColorPicker;
import me.m56738.easyarmorstands.element.MenuElement;
import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.menu.MenuClickInterceptor;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;

public class ColorPickerSlot implements MenuSlot, MenuClickInterceptor {
    private final MenuElement element;
    private boolean active;

    public ColorPickerSlot(MenuElement element) {
        this.element = element;
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = Util.createItem(
                ItemType.LEATHER_CHESTPLATE,
                Component.text("Open color picker", NamedTextColor.BLUE),
                active ? Arrays.asList(
                        Component.text("Click the item whose", NamedTextColor.GRAY),
                        Component.text("color you want to edit.", NamedTextColor.GRAY)
                ) : Arrays.asList(
                        Component.text("Click to edit the", NamedTextColor.GRAY),
                        Component.text("color of an item.", NamedTextColor.GRAY)
                )
        );
        ItemMeta meta = item.getItemMeta();
        if (active) {
            LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
            armorMeta.setColor(Color.YELLOW);
            armorMeta.addItemFlags(ItemFlag.values());
        }
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void onClick(MenuClick click) {
        if (active) {
            active = false;
            click.updateItem();
            return;
        }

        if (!click.isLeftClick() || click.cursor().getType() != Material.AIR) {
            return;
        }

        Menu menu = click.menu();
        int size = menu.getSize();
        ItemPropertySlot foundSlot = null;
        int foundSlots = 0;
        for (int i = 0; i < size; i++) {
            MenuSlot slot = menu.getSlot(i);
            if (slot instanceof ItemPropertySlot) {
                ItemPropertySlot itemSlot = (ItemPropertySlot) slot;
                if (isApplicable(itemSlot)) {
                    foundSlot = itemSlot;
                    foundSlots++;
                }
            }
        }

        if (foundSlots == 1) {
            // Found exactly one applicable item property slot
            click.open(ColorPicker.create(foundSlot, () -> element.openMenu(click.player())).getInventory());
        } else {
            // Ask the user to click a slot
            active = true;
            click.interceptNextClick(this);
        }

        click.updateItem();
    }

    @Override
    public void interceptClick(MenuClick click) {
        active = false;
        click.updateItem(this);

        if (!click.isLeftClick() || click.cursor().getType() != Material.AIR) {
            return;
        }

        MenuSlot slot = click.slot();
        if (slot instanceof ItemPropertySlot) {
            ItemPropertySlot itemSlot = (ItemPropertySlot) slot;
            if (isApplicable(itemSlot)) {
                click.open(ColorPicker.create(itemSlot, () -> element.openMenu(click.player())).getInventory());
            }
        }
    }

    private boolean isApplicable(ItemPropertySlot slot) {
        ItemStack item = slot.getItem();
        if (item == null) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        return EasyArmorStands.getInstance().getCapability(ItemColorCapability.class).hasColor(meta);
    }
}
