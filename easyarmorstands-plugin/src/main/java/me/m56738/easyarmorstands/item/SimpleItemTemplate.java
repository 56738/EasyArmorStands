package me.m56738.easyarmorstands.item;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.util.ItemTemplate;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class SimpleItemTemplate implements ItemTemplate {
    private final ItemStack template;
    private final String displayName;
    private final List<String> lore;
    private final TagResolver resolver;
    private final ItemRenderer renderer;

    public SimpleItemTemplate(ItemStack template, String displayName, List<String> lore, TagResolver resolver, ItemRenderer renderer) {
        this.template = template.clone();
        this.displayName = displayName;
        this.lore = Collections.unmodifiableList(new ArrayList<>(lore));
        this.resolver = resolver;
        this.renderer = renderer;
    }

    private Component renderName(Locale locale, TagResolver resolver) {
        return renderer.renderName(displayName, locale, resolver);
    }

    private List<Component> renderLore(Locale locale, TagResolver resolver) {
        List<Component> renderedLore = new ArrayList<>(lore.size());
        for (String line : lore) {
            renderedLore.add(renderer.renderLore(line, locale, resolver));
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

        ComponentCapability componentCapability = EasyArmorStandsPlugin.getInstance().getCapability(ComponentCapability.class);

        ItemStack item = template.clone();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (displayName != null) {
                componentCapability.setDisplayName(meta, renderName(locale, resolver));
            }
            componentCapability.setLore(meta, renderLore(locale, resolver));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
        }
        return item;
    }

    public SimpleItemTemplate appendLore(List<String> lore) {
        List<String> newLore = new ArrayList<>(this.lore.size() + lore.size());
        newLore.addAll(this.lore);
        newLore.addAll(lore);
        return new SimpleItemTemplate(template, displayName, newLore, resolver, renderer);
    }

    public SimpleItemTemplate editMeta(Consumer<ItemMeta> consumer) {
        ItemStack newTemplate = template.clone();
        ItemMeta meta = newTemplate.getItemMeta();
        if (meta != null) {
            consumer.accept(meta);
            newTemplate.setItemMeta(meta);
        }
        return new SimpleItemTemplate(newTemplate, displayName, lore, resolver, renderer);
    }

    public SimpleItemTemplate addResolver(TagResolver resolver) {
        return new SimpleItemTemplate(template, displayName, lore, TagResolver.resolver(
                this.resolver,
                resolver), renderer);
    }

    public SimpleItemTemplate withTemplate(ItemStack template) {
        return new SimpleItemTemplate(template, displayName, lore, resolver, renderer);
    }

    public SimpleItemTemplate withFallbackTemplate(ItemStack template) {
        if (this.template.getType() != Material.AIR && this.template.getAmount() != 0) {
            // already have a template
            return this;
        }
        return withTemplate(template);
    }

    public SimpleItemTemplate withRenderer(ItemRenderer renderer) {
        return new SimpleItemTemplate(template, displayName, lore, resolver, renderer);
    }

    public Material getType() {
        return template.getType();
    }
}
