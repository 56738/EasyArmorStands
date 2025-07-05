package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuClickInterceptor;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.color.ColorPickerContextImpl;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ColorPickerSlot implements MenuSlot, MenuClickInterceptor {
    private final SimpleItemTemplate itemTemplate;
    private final SimpleItemTemplate activeItemTemplate;
    private final TagResolver resolver;
    private final MenuElement element;
    private boolean active;

    public ColorPickerSlot(SimpleItemTemplate itemTemplate, SimpleItemTemplate activeItemTemplate, TagResolver resolver, MenuElement element) {
        this.itemTemplate = itemTemplate;
        this.activeItemTemplate = activeItemTemplate;
        this.resolver = resolver;
        this.element = element;
    }

    private SimpleItemTemplate getItemTemplate() {
        return active ? activeItemTemplate : itemTemplate;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        ItemStack item = getItemTemplate().render(locale, resolver);
        if (active) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;
                armorMeta.setColor(Color.YELLOW);
                item.setItemMeta(meta);
            }
        }
        return item;
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
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
            open(click.player(), foundSlot);
        } else {
            // Ask the user to click a slot
            active = true;
            click.interceptNextClick(this);
        }

        click.updateItem();
    }

    @Override
    public void interceptClick(@NotNull MenuClick click) {
        active = false;
        click.updateItem(this);

        if (!click.isLeftClick() || click.cursor().getType() != Material.AIR) {
            return;
        }

        MenuSlot slot = click.slot();
        if (slot instanceof ItemPropertySlot) {
            ItemPropertySlot itemSlot = (ItemPropertySlot) slot;
            if (isApplicable(itemSlot)) {
                open(click.player(), itemSlot);
            }
        }
    }

    private void open(Player player, ItemPropertySlot itemSlot) {
        Menu menu = EasyArmorStandsPlugin.getInstance().createColorPicker(player, new ColorPickerContextImpl(itemSlot));
        menu.addCloseListener((p, m) -> element.openMenu(p));
        player.openInventory(menu.getInventory());
    }

    private boolean isApplicable(ItemPropertySlot slot) {
        ItemStack item = slot.getProperty().getValue();
        ItemMeta meta = item.getItemMeta();
        return meta instanceof LeatherArmorMeta || meta instanceof MapMeta;
    }
}
