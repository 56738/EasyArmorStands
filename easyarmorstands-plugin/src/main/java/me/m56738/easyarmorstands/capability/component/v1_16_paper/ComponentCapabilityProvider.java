package me.m56738.easyarmorstands.capability.component.v1_16_paper;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ComponentCapabilityProvider implements CapabilityProvider<ComponentCapability> {
    @Override
    public boolean isSupported() {
        try {
            Entity.class.getMethod("customName");
            Entity.class.getMethod("customName", Component.class);
            ItemMeta.class.getMethod("displayName", Component.class);
            ItemMeta.class.getMethod("lore", List.class);
            ItemStack.class.getMethod("displayName");
            Bukkit.class.getMethod("createInventory", InventoryHolder.class, int.class, Component.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public ComponentCapability create(Plugin plugin) {
        return new ComponentCapabilityImpl();
    }

    private static class ComponentCapabilityImpl implements ComponentCapability {
        private static final Style fallbackStyle = Style.style(
                NamedTextColor.WHITE,
                TextDecoration.ITALIC.withState(false));

        public ComponentCapabilityImpl() {
        }

        private static @Nullable Component style(@Nullable Component component) {
            if (component == null) {
                return null;
            }
            return component.applyFallbackStyle(fallbackStyle);
        }

        @Override
        public @Nullable Component getCustomName(@NotNull Entity entity) {
            return entity.customName();
        }

        @Override
        public void setCustomName(@NotNull Entity entity, @Nullable Component name) {
            entity.customName(name);
        }

        @Override
        public void setDisplayName(@NotNull ItemMeta meta, @Nullable Component displayName) {
            meta.displayName(style(displayName));
        }

        @Override
        public void setLore(@NotNull ItemMeta meta, @NotNull List<@NotNull Component> lore) {
            meta.lore(lore);
        }

        @Override
        public @NotNull Component getItemDisplayName(@NotNull ItemStack item) {
            return item.displayName();
        }

        @Override
        public @NotNull Inventory createInventory(InventoryHolder holder, int size, Component title) {
            return Bukkit.createInventory(holder, size, title);
        }
    }
}
