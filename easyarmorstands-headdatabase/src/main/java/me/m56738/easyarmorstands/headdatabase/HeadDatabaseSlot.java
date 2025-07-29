package me.m56738.easyarmorstands.headdatabase;

import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class HeadDatabaseSlot implements MenuSlot {
    private final SimpleItemTemplate itemTemplate;
    private final TagResolver resolver;

    public HeadDatabaseSlot(SimpleItemTemplate itemTemplate, TagResolver resolver) {
        this.itemTemplate = itemTemplate;
        this.resolver = resolver;
    }

    @Override
    public Item getItem(Locale locale) {
        return itemTemplate.render(locale, resolver);
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (!click.isLeftClick()) {
            return;
        }
        click.queueTask(() -> {
            Player player = PaperPlayer.toNative(click.player());
            player.setItemOnCursor(null);
            player.performCommand("headdb");
        });
    }
}
