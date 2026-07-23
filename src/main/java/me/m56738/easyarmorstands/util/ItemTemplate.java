package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.Locale;

public interface ItemTemplate {
    ItemStack render(Locale locale);

    ItemStack render(Locale locale, TagResolver resolver);
}
