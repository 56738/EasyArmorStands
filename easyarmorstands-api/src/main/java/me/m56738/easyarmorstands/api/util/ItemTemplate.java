package me.m56738.easyarmorstands.api.util;

import me.m56738.easyarmorstands.api.platform.inventory.Item;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.Locale;

public interface ItemTemplate {
    Item render(Locale locale);

    Item render(Locale locale, TagResolver resolver);
}
