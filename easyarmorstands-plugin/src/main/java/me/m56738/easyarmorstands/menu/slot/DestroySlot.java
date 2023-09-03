package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.item.ItemTemplate;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class DestroySlot implements MenuSlot {
    private final DestroyableElement element;
    private final ItemTemplate itemTemplate;
    private final TagResolver resolver;

    public DestroySlot(DestroyableElement element, ItemTemplate itemTemplate, TagResolver resolver) {
        this.element = element;
        this.itemTemplate = itemTemplate;
        this.resolver = resolver;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return itemTemplate.render(locale, resolver);
    }

    @Override
    public void onClick(MenuClick click) {
        if (!click.isLeftClick()) {
            return;
        }

        ChangeContext context = new EasPlayer(click.player());
        if (!context.canDestroyElement(element)) {
            return;
        }

        context.history().push(new ElementDestroyAction(element));
        element.destroy();
        click.close();
    }
}
