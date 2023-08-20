package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class ItemTemplate {
    private final ItemStack template;
    private final String displayName;
    private final List<String> lore;
    private final TagResolver resolver;

    public ItemTemplate(ItemStack template, String displayName, List<String> lore, TagResolver resolver) {
        this.template = template.clone();
        this.displayName = displayName;
        this.lore = Collections.unmodifiableList(new ArrayList<>(lore));
        this.resolver = resolver;
    }

    public ItemTemplate(ItemTemplate template) {
        this.template = template.template.clone();
        this.displayName = template.displayName;
        this.lore = template.lore;
        this.resolver = template.resolver;
    }

    protected Component renderName(String name, Locale locale, TagResolver resolver) {
        return GlobalTranslator.render(miniMessage().deserialize(name, resolver), locale);
    }

    protected Component renderLoreLine(String line, Locale locale, TagResolver resolver) {
        return GlobalTranslator.render(miniMessage().deserialize(line, resolver), locale);
    }

    protected List<Component> renderLore(List<String> lore, Locale locale, TagResolver resolver) {
        List<Component> renderedLore = new ArrayList<>(lore.size());
        for (String line : lore) {
            renderedLore.add(renderLoreLine(line, locale, resolver));
        }
        return renderedLore;
    }

    public ItemStack render(Locale locale) {
        return render(locale, TagResolver.empty());
    }

    public ItemStack render(Locale locale, TagResolver resolver) {
        resolver = TagResolver.builder()
                .resolver(this.resolver)
                .resolver(resolver)
                .build();

        ComponentCapability componentCapability = EasyArmorStands.getInstance().getCapability(ComponentCapability.class);
        List<Component> renderedLore = renderLore(lore, locale, resolver);

        ItemStack item = template.clone();
        ItemMeta meta = item.getItemMeta();
        if (displayName != null) {
            componentCapability.setDisplayName(meta, renderName(displayName, locale, resolver));
        }
        componentCapability.setLore(meta, renderedLore);
        meta.addItemFlags(ItemFlag.values());
        item.setItemMeta(meta);
        return item;
    }

    public ItemTemplate appendLore(List<String> lore) {
        List<String> newLore = new ArrayList<>(this.lore.size() + lore.size());
        newLore.addAll(this.lore);
        newLore.addAll(lore);
        return new ItemTemplate(template, displayName, newLore, resolver);
    }

    public ItemTemplate editMeta(Consumer<ItemMeta> consumer) {
        ItemStack newTemplate = template.clone();
        ItemMeta meta = newTemplate.getItemMeta();
        if (meta != null) {
            consumer.accept(meta);
            newTemplate.setItemMeta(meta);
        }
        return new ItemTemplate(newTemplate, displayName, lore, resolver);
    }

    public ItemTemplate addResolver(TagResolver resolver) {
        return new ItemTemplate(template, displayName, lore, TagResolver.resolver(
                this.resolver,
                resolver));
    }

    public Material getType() {
        return template.getType();
    }
}
