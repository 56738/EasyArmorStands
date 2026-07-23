package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.EasyArmorStands;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.click.MenuClick;
import me.m56738.easyarmorstands.menu.click.MenuClickInterceptor;
import me.m56738.easyarmorstands.message.MessageStyle;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.registry.ItemTypeKeys;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class ColorPickerSlot implements MenuSlot, MenuClickInterceptor {
    public static final Key KEY = EasyArmorStands.key("color_picker");
    private final EasyArmorStandsCommon eas;
    private final MenuElement element;
    private boolean active;

    public ColorPickerSlot(EasyArmorStandsCommon eas, MenuElement element) {
        this.eas = eas;
        this.element = element;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        ItemStack item = eas.platform().getItemType(ItemTypeKeys.BRUSH).createItemStack();
        if (active) {
            item = item.withEnchantmentGlintOverride(true);
            item = item.withLore(List.of(MenuButtonSlot.format(Component.translatable("easyarmorstands.menu.color-picker.click-target"), MessageStyle.BUTTON_DESCRIPTION, locale)));
        }
        item = item.withCustomName(MenuButtonSlot.format(Component.translatable("easyarmorstands.menu.color-picker.open"), MessageStyle.BUTTON_NAME, locale));
        return item;
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (active) {
            active = false;
            click.updateItem();
            return;
        }

        if (!click.isLeftClick() || !click.cursor().isEmpty()) {
            return;
        }

        Menu menu = click.menu();
        int size = menu.getSize();
        ItemPropertySlot foundSlot = null;
        int foundSlots = 0;
        for (int i = 0; i < size; i++) {
            MenuSlot slot = menu.getSlot(i);
            if (slot instanceof ItemPropertySlot itemSlot) {
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

        if (!click.isLeftClick() || !click.cursor().isEmpty()) {
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
        Menu menu = eas.createColorPicker(player, itemSlot.getProperty());
        menu.addCloseListener((p, m) -> element.openMenu(p));
        player.openInventory(menu.getInventory());
    }

    private boolean isApplicable(ItemPropertySlot slot) {
        ItemStack item = slot.getProperty().getValue();
        return eas.isColorPickerSupported(item);
    }
}
