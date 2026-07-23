package me.m56738.easyarmorstands.item;

import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.util.ItemTemplate;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SimpleItemTemplate implements ItemTemplate {
    private static final Style NAME_FALLBACK_STYLE = Style.style(TextDecoration.ITALIC.withState(false));
    private static final Style LORE_FALLBACK_STYLE = Style.style(TextDecoration.ITALIC.withState(false));

    private final ItemStack template;
    private final String displayName;
    private final List<String> lore;
    private final TagResolver resolver;
    private final ItemRenderer renderer;

    public SimpleItemTemplate(ItemStack template, String displayName, List<String> lore, TagResolver resolver, ItemRenderer renderer) {
        this.template = template;
        this.displayName = displayName;
        this.lore = Collections.unmodifiableList(new ArrayList<>(lore));
        this.resolver = resolver;
        this.renderer = renderer;
    }

    private Component renderName(Locale locale, TagResolver resolver) {
        return renderer.renderName(displayName, locale, resolver)
                .applyFallbackStyle(NAME_FALLBACK_STYLE);
    }

    private List<Component> renderLore(Locale locale, TagResolver resolver) {
        List<Component> renderedLore = new ArrayList<>(lore.size());
        for (String line : lore) {
            renderedLore.add(renderer.renderLore(line, locale, resolver)
                    .applyFallbackStyle(LORE_FALLBACK_STYLE));
        }
        return renderedLore;
    }

    @Override
    public ItemStack render(Locale locale) {
        return render(locale, TagResolver.empty());
    }

    @Override
    public ItemStack render(Locale locale, TagResolver resolver) {
        resolver = TagResolver.builder()
                .resolver(this.resolver)
                .resolver(resolver)
                .build();

        return template
                .withCustomName(renderName(locale, resolver))
                .withLore(renderLore(locale, resolver));
    }
}
