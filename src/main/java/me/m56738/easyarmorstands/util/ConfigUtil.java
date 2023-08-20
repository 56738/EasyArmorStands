package me.m56738.easyarmorstands.util;

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
        ItemStack item;
        String name;
        List<String> lore;
        if (section.isConfigurationSection(path)) {
            ConfigurationSection valueSection = section.getConfigurationSection(path);
            item = createItem(valueSection, "type");
            name = valueSection.getString("name");
            lore = valueSection.getStringList("description");
            ItemMeta meta = item.getItemMeta();
            if (meta instanceof PotionMeta) {
                PotionEffectType effect = PotionEffectType.getByName(valueSection.getString("effect"));
                ((PotionMeta) meta).setMainEffect(effect);
                item.setItemMeta(meta);
            }
        } else {
            item = createItem(section, path);
            name = null;
            lore = Collections.emptyList();
        }
        return new ItemTemplate(item, name, lore);
    }

    private static ItemStack createItem(ConfigurationSection section, String path) {
        if (section.isList(path)) {
            for (String type : section.getStringList(path)) {
                ItemStack item = createItem(type);
                if (item != null) {
                    return item;
                }
            }
        }

        ItemStack item = createItem(section.getString(path));
        if (item != null) {
            return item;
        }

        return new ItemStack(Material.STONE);
    }

    @SuppressWarnings("deprecation")
    private static ItemStack createItem(String type) {
        String[] parts = type.split(":", 2);
        Material material = Material.matchMaterial(parts[0]);
        if (material == null) {
            return null;
        }
        ItemStack item = new ItemStack(material);
        if (parts.length >= 2) {
            short durability;
            try {
                durability = Short.parseShort(parts[1]);
            } catch (NumberFormatException e) {
                durability = DyeColor.valueOf(parts[1]).getWoolData();
            }
            item.setDurability(durability);
        }
        return item;
    }
}
