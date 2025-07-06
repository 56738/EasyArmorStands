package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.element.DestroyableElement;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.history.History;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class DestroySlot implements MenuSlot {
    private final DestroyableElement element;
    private final SimpleItemTemplate itemTemplate;
    private final TagResolver resolver;

    public DestroySlot(DestroyableElement element, SimpleItemTemplate itemTemplate, TagResolver resolver) {
        this.element = element;
        this.itemTemplate = itemTemplate;
        this.resolver = resolver;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return itemTemplate.render(locale, resolver);
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (!click.isLeftClick()) {
            return;
        }

        if (!EasyArmorStandsPlugin.getInstance().canDestroyElement(click.player(), element)) {
            return;
        }

        History history = EasyArmorStandsPlugin.getInstance().getHistory(click.player());
        history.push(new ElementDestroyAction(element));
        element.destroy();
        click.close();
    }
}
