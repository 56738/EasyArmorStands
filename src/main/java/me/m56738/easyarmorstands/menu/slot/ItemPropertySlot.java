package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.menu.button.MenuIcon;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.menu.click.MenuClick;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.registry.ItemTypeKeys;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class ItemPropertySlot implements MenuSlot {
    private final EasyArmorStandsCommon eas;
    private final Property<ItemStack> property;

    public ItemPropertySlot(EasyArmorStandsCommon eas, Property<ItemStack> property) {
        this.eas = eas;
        this.property = property;
    }

    public Property<ItemStack> getProperty() {
        return property;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        ItemStack item = property.getValue();
        if (item.isEmpty()) {
            return MenuButtonSlot.createItem(
                    MenuIcon.of(eas.platform().getItemType(ItemTypeKeys.GLASS_PANE)),
                    property.getType().getName(),
                    Component.empty(),
                    List.of(),
                    locale);
        } else {
            return item.withCustomName(MenuButtonSlot.formatCustomName(property.getType().getName(), item.effectiveName(), locale));
        }
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (click.isShiftClick()) {
            eas.getClipboard(click.player())
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
