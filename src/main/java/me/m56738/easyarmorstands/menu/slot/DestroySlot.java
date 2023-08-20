package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.element.DestroyableElement;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.menu.MenuClick;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.format.NamedTextColor;
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
                Message.component("easyarmorstands.menu.destroy").color(NamedTextColor.BLUE),
                Collections.singletonList(
                        Message.component("easyarmorstands.menu.destroy.description").color(NamedTextColor.GRAY)),
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
