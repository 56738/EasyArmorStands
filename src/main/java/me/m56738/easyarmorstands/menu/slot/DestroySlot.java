package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.element.DestroyableElement;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Locale;

public class DestroySlot implements MenuSlot {
    private final DestroyableElement element;

    public DestroySlot(DestroyableElement element) {
        this.element = element;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return Util.createItem(
                ItemType.TNT,
                Message.buttonName("easyarmorstands.menu.destroy"),
                Collections.singletonList(
                        Message.buttonDescription("easyarmorstands.menu.destroy.description")),
                locale
        );
    }

    @Override
    public void onClick(MenuClick click) {
        if (!click.isLeftClick()) {
            return;
        }

        ChangeContext context = click.player();
        if (!context.canDestroyElement(element)) {
            return;
        }

        context.history().push(new ElementDestroyAction(element));
        element.destroy();
        click.close();
    }
}
