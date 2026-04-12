package me.m56738.easyarmorstands.menu.slot;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.message.MessageStyle;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class ItemPropertySlot implements MenuSlot {
    private final Property<ItemStack> property;

    public ItemPropertySlot(Property<ItemStack> property) {
        this.property = property;
    }

    public Property<ItemStack> getProperty() {
        return property;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public ItemStack getItem(Locale locale) {
        ItemStack item = Util.wrapItem(property.getValue());
        if (item.isEmpty()) {
            item = MenuButtonSlot.createItem(
                    MenuIcon.of(Material.GLASS_PANE),
                    property.getType().getName(),
                    List.of(),
                    locale);
        } else {
            item.setData(DataComponentTypes.ITEM_NAME, Component.text()
                    .append(MenuButtonSlot.format(property.getType().getName(), MessageStyle.BUTTON_NAME, locale))
                    .append(Component.text(": ", MenuButtonSlot.FALLBACK_STYLE))
                    .append(item.effectiveName())
                    .build());
        }
        return item;
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (click.isShiftClick()) {
            EasyArmorStandsPlugin.getInstance().getClipboard(click.player())
                    .handlePropertyShiftClick(property);
            return;
        }

        if (!property.canChange(click.player())) {
            return;
        }

        if (!click.isLeftClick()) {
            return;
        }

        click.queueTask(() -> {
            // Event is still cancelled, swap the items ourselves to prevent duplication
            ItemStack itemInCursor = click.cursor();
            ItemStack itemInProperty = property.getValue();
            if (property.setValue(itemInCursor)) {
                click.player().setItemOnCursor(itemInProperty);
                property.commit();
                click.menu().updateItem(click.index());
            }
        });
    }
}
