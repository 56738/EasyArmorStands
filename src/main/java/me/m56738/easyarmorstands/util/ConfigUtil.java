package me.m56738.easyarmorstands.util;

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class ConfigUtil {
    public static ItemTemplate getItem(ConfigurationSection section, String path) {
        return getItem(section, path, new ItemStack(Material.STONE));
    }

    public static ItemTemplate getItem(ConfigurationSection section, String path, ItemStack template) {
        ItemStack item = template.clone();
        String name;
        List<String> lore;
        if (section.isConfigurationSection(path)) {
            ConfigurationSection valueSection = section.getConfigurationSection(path);
            configureType(item, valueSection, "type");
            name = valueSection.getString("name");
            lore = valueSection.getStringList("description");
            ItemMeta meta = item.getItemMeta();
            if (meta instanceof PotionMeta) {
                PotionEffectType effect = PotionEffectType.getByName(valueSection.getString("effect"));
                ((PotionMeta) meta).setMainEffect(effect);
                item.setItemMeta(meta);
            }
        } else {
            configureType(item, section, path);
            name = null;
            lore = Collections.emptyList();
        }
        return new ItemTemplate(item, name, lore, TagResolver.empty());
    }

    public static ButtonTemplate getButton(ConfigurationSection section, String path) {
        return new ButtonTemplate(getItem(section, path));
    }

    public static ButtonTemplate getButton(ConfigurationSection section, String path, ItemStack template) {
        return new ButtonTemplate(getItem(section, path, template));
    }

    private static void configureType(ItemStack item, ConfigurationSection section, String path) {
        if (section.isList(path)) {
            for (String type : section.getStringList(path)) {
                if (configureType(item, type)) {
                    return;
                }
            }
        } else if (section.isString(path)) {
            configureType(item, section.getString(path));
        }
    }

    @SuppressWarnings("deprecation")
    private static boolean configureType(ItemStack item, String type) {
        String[] parts = type.split(":", 2);
        Material material = Material.matchMaterial(parts[0]);
        if (material == null) {
            return false;
        }
        item.setType(material);
        if (parts.length >= 2) {
            short durability;
            try {
                durability = Short.parseShort(parts[1]);
            } catch (NumberFormatException e) {
                durability = DyeColor.valueOf(parts[1]).getWoolData();
            }
            item.setDurability(durability);
        }
        return true;
    }
}
