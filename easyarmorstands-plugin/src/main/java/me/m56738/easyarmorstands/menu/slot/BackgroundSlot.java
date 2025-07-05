package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class BackgroundSlot implements MenuSlot {
    private final SimpleItemTemplate itemTemplate;
    private final TagResolver resolver;

    public BackgroundSlot(SimpleItemTemplate itemTemplate, TagResolver resolver) {
        this.itemTemplate = itemTemplate;
        this.resolver = resolver;
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return itemTemplate.render(locale, resolver);
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
        if (click.isRightClick() && click.cursor().getType() == Material.AIR) {
            click.close();
        }
    }
}
