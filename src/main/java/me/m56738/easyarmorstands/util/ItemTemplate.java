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
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

public class ItemTemplate {
    private final ItemStack template;
    private final String displayName;
    private final List<String> lore;

    public ItemTemplate(ItemStack template, String displayName, List<String> lore) {
        this.template = template.clone();
        this.displayName = displayName;
        this.lore = new ArrayList<>(lore);
    }

    public ItemStack render(Locale locale, TagResolver resolver) {
        ComponentCapability componentCapability = EasyArmorStands.getInstance().getCapability(ComponentCapability.class);
        List<Component> renderedLore = new ArrayList<>(lore.size());
        for (String line : lore) {
            renderedLore.add(GlobalTranslator.render(miniMessage().deserialize(line, resolver), locale));
        }

        ItemStack item = template.clone();
        ItemMeta meta = item.getItemMeta();
        if (displayName != null) {
            componentCapability.setDisplayName(meta, GlobalTranslator.render(miniMessage().deserialize(displayName, resolver), locale));
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
        return new ItemTemplate(template, displayName, newLore);
    }

    public ItemTemplate editMeta(Consumer<ItemMeta> consumer) {
        ItemStack newTemplate = template.clone();
        ItemMeta meta = newTemplate.getItemMeta();
        if (meta != null) {
            consumer.accept(meta);
            newTemplate.setItemMeta(meta);
        }
        return new ItemTemplate(newTemplate, displayName, lore);
    }

    public Material getType() {
        return template.getType();
    }
}
