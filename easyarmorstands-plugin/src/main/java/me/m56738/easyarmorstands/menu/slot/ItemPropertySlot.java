package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ItemPropertySlot implements MenuSlot {
    private final Property<ItemStack> property;
    private final PropertyContainer container;

    public ItemPropertySlot(Property<ItemStack> property, PropertyContainer container) {
        this.property = property;
        this.container = container;
    }

    public Property<ItemStack> getProperty() {
        return property;
    }

    public PropertyContainer getContainer() {
        return container;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return Util.wrapItem(property.getValue());
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (click.isShiftClick()) {
            EasyArmorStandsPlugin.getInstance().getClipboard(click.player())
                    .handlePropertyShiftClick(property, click);
            return;
        }

        if (!property.canChange(click.player())) {
            return;
        }

        if (click.player().getGameMode() == GameMode.CREATIVE) {
            // Allow the click event and set the property later
            // Might duplicate items if two players interact in the same tick, so only do this in creative mode
            click.allow();
            click.queueTask(() -> {
                ItemStack item = click.menu().getInventory().getItem(click.index());
                if (property.setValue(Util.wrapItem(item))) {
                    container.commit();
                } else {
                    // Failed to change the property, revert changes
                    // Put the placed item back into the cursor
                    click.player().setItemOnCursor(item);
                    // Refresh the item in the menu
                    click.menu().updateItem(click.index());
                }
            });
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
                container.commit();
                click.menu().updateItem(click.index());
            }
        });
    }
}
