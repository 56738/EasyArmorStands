package me.m56738.easyarmorstands.menu.slot;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.button.MenuButton;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.menu.click.MenuClickContext;
import me.m56738.easyarmorstands.menu.button.MenuSlotButton;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.message.MessageStyle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Locale;

@NullMarked
public class MenuButtonSlot implements MenuSlot {
    public static final Style FALLBACK_STYLE = Style.style(
            NamedTextColor.WHITE,
            TextDecoration.ITALIC.withState(false));
    private final MenuButton button;

    private MenuButtonSlot(MenuButton button) {
        this.button = button;
    }

    public static MenuSlot toSlot(MenuButton button) {
        if (button instanceof MenuSlotButton menuSlotButton) {
            return menuSlotButton.getSlot();
        } else {
            return new MenuButtonSlot(button);
        }
    }

    public MenuButton getButton() {
        return button;
    }

    @Override
    public @Nullable ItemStack getItem(Locale locale) {
        if (button instanceof MenuSlotButton menuSlotButton) {
            return menuSlotButton.getItem(locale);
        }
        return createItem(button.icon(), button.name(), button.description(), locale);
    }

    @Override
    public void onClick(MenuClick click) {
        if (button instanceof MenuSlotButton menuSlotButton) {
            menuSlotButton.getSlot().onClick(click);
            return;
        }
        button.onClick(new MenuClickContext() {
            @Override
            public Player player() {
                return click.player();
            }

            @Override
            public boolean isLeftClick() {
                return click.isLeftClick();
            }

            @Override
            public boolean isRightClick() {
                return click.isRightClick();
            }

            @Override
            public boolean isShiftClick() {
                return click.isShiftClick();
            }

            @Override
            public void closeMenu() {
                click.close();
            }
        });
        click.updateItem();
    }

    @SuppressWarnings("UnstableApiUsage")
    public static ItemStack createItem(MenuIcon icon, Component name, List<Component> description, Locale locale) {
        ItemStack item = icon.asItem().clone();
        item.setData(DataComponentTypes.CUSTOM_NAME, format(name, MessageStyle.BUTTON_NAME, locale));
        item.setData(DataComponentTypes.LORE, ItemLore.lore(description.stream()
                .map(c -> format(c, MessageStyle.BUTTON_DESCRIPTION, locale))
                .toList()));
        item.unsetData(DataComponentTypes.POTION_CONTENTS);
        return item;
    }

    public static Component format(Component component, MessageStyle style, Locale locale) {
        return GlobalTranslator.render(Message.format(style, component), locale).applyFallbackStyle(FALLBACK_STYLE);
    }
}
