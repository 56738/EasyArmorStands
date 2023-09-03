package me.m56738.easyarmorstands.capability.component.v1_8;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ComponentCapabilityProvider implements CapabilityProvider<ComponentCapability> {
    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public Priority getPriority() {
        return Priority.FALLBACK;
    }

    @Override
    public ComponentCapability create(Plugin plugin) {
        return new ComponentCapabilityImpl();
    }

    private static class ComponentCapabilityImpl implements ComponentCapability {
        private static final Style fallbackStyle = Style.style(
                NamedTextColor.WHITE,
                TextDecoration.ITALIC.withState(false));

        private final LegacyComponentSerializer serializer = BukkitComponentSerializer.legacy();

        private static Component style(Component component) {
            if (component == null) {
                return null;
            }
            return component.applyFallbackStyle(fallbackStyle);
        }

        @Override
        public Component getCustomName(Entity entity) {
            return serializer.deserializeOrNull(entity.getCustomName());
        }

        @Override
        public void setCustomName(Entity entity, Component name) {
            entity.setCustomName(serializer.serializeOrNull(name));
        }

        @Override
        public void setDisplayName(ItemMeta meta, Component displayName) {
            if (displayName instanceof TextComponent && ((TextComponent) displayName).content().isEmpty()) {
                meta.setDisplayName(ChatColor.RESET.toString());
                return;
            }
            meta.setDisplayName(serializer.serializeOrNull(style(displayName)));
        }

        @Override
        public void setLore(ItemMeta meta, List<Component> lore) {
            List<String> legacyLore = new ArrayList<>(lore.size());
            for (Component component : lore) {
                legacyLore.add(serializer.serialize(style(component)));
            }
            meta.setLore(legacyLore);
        }

        @Override
        public Component getItemDisplayName(ItemStack item) {
            return Component.text(item.getType().name());
        }

        @Override
        public Inventory createInventory(InventoryHolder holder, int size, Component title) {
            return Bukkit.createInventory(holder, size, serializer.serialize(title));
        }
    }
}
