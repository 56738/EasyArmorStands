package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ItemPropertySlot implements MenuSlot {
    private final Element element;
    private final PropertyType<Item> type;
    private final Property<Item> untrackedProperty;

    public ItemPropertySlot(Element element, PropertyType<Item> type) {
        this.element = element;
        this.type = type;
        this.untrackedProperty = element.getProperties().get(type);
    }

    public Element getElement() {
        return element;
    }

    public PropertyType<Item> getType() {
        return type;
    }

    @Override
    public Item getItem(Locale locale) {
        return getElement().getProperties().get(getType()).getValue();
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (click.isShiftClick()) {
            EasyArmorStandsPlugin.getInstance().getClipboard(click.player())
                    .handlePropertyShiftClick(untrackedProperty, click);
            return;
        }

        if (!untrackedProperty.canChange(click.player())) {
            return;
        }

        if (click.player().isCreativeMode()) {
            // Allow the click event and set the property later
            // Might duplicate items if two players interact in the same tick, so only do this in creative mode
            click.allow();
            click.queueTask(() -> {
                // TODO
//                ItemStack item = click.menu().getInventory().getItem(click.index());
//                try (ManagedChangeContext context = EasyArmorStands.get().changeContext().create(PaperPlayer.fromNative(click.player()))) {
//                    Property<ItemStack> property = context.getProperties(element).get(type);
//                    if (property.setValue(LegacyUtil.wrapItem(item))) {
//                        context.commit();
//                    } else {
//                        // Failed to change the property, revert changes
//                        // Put the placed item back into the cursor
//                        click.player().setItemOnCursor(item);
//                        // Refresh the item in the menu
//                        click.menu().updateItem(click.index());
//                    }
//                }
            });
            return;
        }

        if (!click.isLeftClick()) {
            return;
        }

        click.queueTask(() -> {
            // Event is still cancelled, swap the items ourselves to prevent duplication
            // TODO
//            try (ManagedChangeContext context = EasyArmorStands.get().changeContext().create(PaperPlayer.fromNative(click.player()))) {
//                Property<ItemStack> property = context.getProperties(element).get(type);
//                ItemStack itemInCursor = click.cursor();
//                ItemStack itemInProperty = property.getValue();
//                if (property.setValue(itemInCursor)) {
//                    click.player().setItemOnCursor(itemInProperty);
//                    context.commit();
//                    click.menu().updateItem(click.index());
//                }
//            }
        });
    }
}
