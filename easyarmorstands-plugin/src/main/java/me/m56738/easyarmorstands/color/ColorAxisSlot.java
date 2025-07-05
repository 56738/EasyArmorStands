package me.m56738.easyarmorstands.color;

import me.m56738.easyarmorstands.api.menu.ColorPickerContext;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class ColorAxisSlot implements ColorSlot {
    private final SimpleItemTemplate itemTemplate;
    private final TagResolver resolver;

    public ColorAxisSlot(ColorPickerContext context, ColorAxis axis, SimpleItemTemplate itemTemplate, TagResolver resolver) {
        this.itemTemplate = itemTemplate;
        this.resolver = TagResolver.builder()
                .resolver(resolver)
                .tag("color", new ColorPickerColorTag(context))
                .tag("value", new ColorPickerAxisTag(context, axis))
                .build();
    }

    @Override
    public ItemStack getItem(Locale locale) {
        return itemTemplate.render(locale, resolver);
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
    }
}
