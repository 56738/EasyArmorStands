package me.m56738.easyarmorstands.menu.button;

import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuButtonCategory;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.menu.slot.MenuButtonSlot;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Locale;

@NullMarked
public class MenuSlotButton implements MenuButton {
    private final Key key;
    private final MenuSlot slot;
    private final MenuButtonCategory category;

    private MenuSlotButton(Key key, MenuSlot slot, MenuButtonCategory category) {
        this.key = key;
        this.slot = slot;
        this.category = category;
    }

    public static MenuButton toButton(Key key, MenuSlot slot) {
        return toButton(key, slot, MenuButtonCategory.DEFAULT);
    }

    public static MenuButton toButton(Key key, MenuSlot slot, MenuButtonCategory category) {
        if (slot instanceof MenuButtonSlot menuButtonSlot) {
            return menuButtonSlot.getButton();
        } else {
            return new MenuSlotButton(key, slot, category);
        }
    }

    public MenuSlot getSlot() {
        return slot;
    }

    @Override
    public Key key() {
        return key;
    }

    @Override
    public MenuIcon icon() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MenuButtonCategory category() {
        return category;
    }

    @Override
    public Component name() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Component> description() {
        throw new UnsupportedOperationException();
    }

    public ItemStack getItem(Locale locale) {
        return Util.wrapItem(slot.getItem(locale));
    }

    @Override
    public void onClick(MenuClickContext context) {
        throw new UnsupportedOperationException();
    }
}
