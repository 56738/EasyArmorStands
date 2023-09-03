package me.m56738.easyarmorstands.headdatabase;

import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.item.ItemTemplate;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class HeadDatabaseSlot implements MenuSlot {
    private final ItemTemplate itemTemplate;
    private final TagResolver resolver;

    public HeadDatabaseSlot(ItemTemplate itemTemplate, TagResolver resolver) {
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
        click.queueTask(() -> {
            Player player = click.player();
            player.setItemOnCursor(null);
            player.performCommand("headdb");
        });
    }
}
