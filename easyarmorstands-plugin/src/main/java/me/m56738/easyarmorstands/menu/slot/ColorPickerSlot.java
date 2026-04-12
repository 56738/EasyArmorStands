package me.m56738.easyarmorstands.menu.slot;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuClickInterceptor;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.color.ColorPickerContextImpl;
import me.m56738.easyarmorstands.message.MessageStyle;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ColorPickerSlot implements MenuSlot, MenuClickInterceptor {
    public static final Key KEY = EasyArmorStands.key("color_picker");
    private final MenuElement element;
    private boolean active;

    public ColorPickerSlot(MenuElement element) {
        this.element = element;
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public ItemStack getItem(Locale locale) {
        ItemStack item = ItemStack.of(Material.BRUSH);
        ItemLore.Builder lore = ItemLore.lore();
        if (active) {
            item.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
            lore.addLine(MenuButtonSlot.format(Component.translatable("easyarmorstands.menu.color-picker.click-target"), MessageStyle.BUTTON_DESCRIPTION, locale));
        }
        item.setData(DataComponentTypes.CUSTOM_NAME, MenuButtonSlot.format(Component.translatable("easyarmorstands.menu.color-picker.open"), MessageStyle.BUTTON_NAME, locale));
        item.setData(DataComponentTypes.LORE, lore.build());
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
        if (slot instanceof ItemPropertySlot itemSlot) {
            if (isApplicable(itemSlot)) {
                open(click.player(), itemSlot);
            }
        }
    }

    private void open(Player player, ItemPropertySlot itemSlot) {
        Menu menu = EasyArmorStandsPlugin.getInstance().createColorPicker(player, new ColorPickerContextImpl(itemSlot.getProperty()));
        menu.addCloseListener((p, m) -> element.openMenu(p));
        player.openInventory(menu.getInventory());
    }

    private boolean isApplicable(ItemPropertySlot slot) {
        ItemStack item = slot.getProperty().getValue();
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        return ColorPickerContextImpl.hasColor(meta);
    }
}
