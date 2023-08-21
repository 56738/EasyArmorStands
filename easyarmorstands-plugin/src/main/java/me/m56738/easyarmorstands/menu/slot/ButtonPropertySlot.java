package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.button.PropertyButton;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class ButtonPropertySlot implements MenuSlot {
    private final PropertyButton button;
    private final Locale locale;

    public ButtonPropertySlot(PropertyButton button, Locale locale) {
        this.button = button;
        this.locale = locale;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return button.createItem(this.locale);
    }

    @Override
    public void onClick(MenuClick click) {
        button.onClick(click);
        click.updateItem();
    }
}
