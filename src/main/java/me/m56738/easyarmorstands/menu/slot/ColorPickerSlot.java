package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.itemcolor.ItemColorCapability;
import me.m56738.easyarmorstands.color.ColorPicker;
import me.m56738.easyarmorstands.element.MenuElement;
import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.menu.MenuClickInterceptor;
import net.kyori.adventure.identity.Identity;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Locale;

public class ColorPickerSlot implements MenuSlot, MenuClickInterceptor {
    private final MenuElement element;
    private boolean active;

    public ColorPickerSlot(MenuElement element) {
        this.element = element;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        ItemStack item = EasyArmorStands.getInstance().getColorPickerButtonTemplate(active).render(locale);
        if (active) {
            ItemMeta meta = item.getItemMeta();
            LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
            armorMeta.setColor(Color.YELLOW);
            item.setItemMeta(meta);
        }
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
            Locale locale = click.player().getOrDefault(Identity.LOCALE, Locale.US);
            click.open(ColorPicker.create(foundSlot, locale, () -> element.openMenu(click.player())).getInventory());
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
                Locale locale = click.player().getOrDefault(Identity.LOCALE, Locale.US);
                click.open(ColorPicker.create(itemSlot, locale, () -> element.openMenu(click.player())).getInventory());
            }
        }
    }

    private boolean isApplicable(ItemPropertySlot slot) {
        ItemStack item = slot.getProperty().getValue();
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
